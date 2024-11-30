package com.booksms.payment.infrastructure.servicegateway;

import com.booksms.payment.core.domain.exception.InvalidSignatureException;
import com.booksms.payment.infrastructure.FeignClient.MomoClient;
import com.booksms.payment.interfaceLayer.dto.*;
import com.booksms.payment.interfaceLayer.service.RedisService.OrderRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Service
@RequiredArgsConstructor
@Slf4j
public class MomoGateway {
    private final MomoClient momoClient;
    private final OrderRedisService orderRedisService;
    @Value("${momo.partner-code}")
    private String partnerCode;
    @Value("${momo.return-url}")
    private String returnUrl;
    @Value("${momo.ipn-url}")
    private String ipnUrl;
    @Value("${momo.access-key}")
    private String accessKey;
    @Value("${momo.secret-key}")
    private String secretKey;


    public MomoPaymentResponse createPayment(PaymentDTO paymentDTO, ResponsePayment responsePayment) throws Exception {
        String requestId = String.valueOf(System.currentTimeMillis());
        String extraData = "";

        String requestType= "payWithMethod";
        // Tạo signature
        String rawSignature = String.format(
                "accessKey=%s&amount=%s&extraData=%s&ipnUrl=%s&orderId=%s&orderInfo=%s&partnerCode=%s&redirectUrl=%s&requestId=%s&requestType=%s",
                accessKey, Math.round(paymentDTO.getTotal()), extraData, ipnUrl, paymentDTO.getOrderNumber(), paymentDTO.getDescription(), partnerCode, returnUrl, requestId, requestType
        );
        String signature = hmacSHA256(rawSignature, secretKey);

        // Tạo request body
        MomoPaymentRequest request = new MomoPaymentRequest();
        request.setPartnerCode(partnerCode);
        request.setAccessKey(accessKey);
        request.setRequestId(requestId);
        request.setAmount(String.valueOf(Math.round(paymentDTO.getTotal())));
        request.setOrderId(String.valueOf(paymentDTO.getOrderNumber()));
        request.setOrderInfo(paymentDTO.getDescription());
        request.setRedirectUrl(returnUrl);
        request.setIpnUrl(ipnUrl);
        request.setExtraData(extraData);
        request.setRequestType(requestType);
        request.setSignature(signature);

        log.info(request.toString());


        responsePayment.setPaymentMethod("MOMO");
        orderRedisService.setValue(String.valueOf(responsePayment.getOrderNumber()),responsePayment);
        MomoPaymentResponse response = momoClient.createPayment(request);
        log.info(response.toString());
        return response;
    }

    private String hmacSHA256(String data, String key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(data.getBytes());
        return Hex.encodeHexString(hmacBytes);
    }

    public Boolean handleResponseIpn(IpnRequest request) {
        try {
            // 1. Xây dựng chuỗi rawSignature để xác thực chữ ký
            String rawSignature = String.format(
                    "accessKey=%s&amount=%s&extraData=%s&message=%s&orderId=%s&orderInfo=%s&orderType=%s&partnerCode=%s&payType=%s&requestId=%s&responseTime=%s&resultCode=%s&transId=%s",
                    request.getAccessKey(), request.getAmount(), request.getExtraData(),
                    request.getMessage(), request.getOrderId(), request.getOrderInfo(),
                    request.getOrderType(), request.getPartnerCode(), request.getPayType(),
                    request.getRequestId(), request.getResponseTime(), request.getResultCode(),
                    request.getTransId()
            );

            // 2. Tạo chữ ký từ rawSignature
            String computedSignature = hmacSHA256(rawSignature, "your-secret-key");

            // 3. Xác minh chữ ký
            if (!computedSignature.equals(request.getSignature())) {
                log.error("Invalid signature for orderId: {}", request.getOrderId());
                throw new InvalidSignatureException("Invalid signature for orderId: " + request.getOrderId());
            }

            // 4. Kiểm tra trạng thái giao dịch
            ResponsePayment responsePayment = orderRedisService.getValue(request.getOrderId());
            if (responsePayment == null) {
                log.error("No data found in Redis for orderId: {}", request.getOrderId());
                return false;
            }

            if (request.getResultCode() == 0) {

                return true;
            } else {


                return false;
            }

        } catch (InvalidSignatureException e) {
            log.error("Signature verification failed: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Error processing IPN request for orderId: {}, error: {}", request.getOrderId(), e.getMessage());
            return false;
        }
    }
}
