package com.booksms.payment.interfaceLayer.dto;

import lombok.Data;

@Data
public class IpnRequest {
    private String partnerCode;    // Mã đối tác do MoMo cung cấp
    private String orderId;        // Mã đơn hàng
    private String requestId;      // Mã yêu cầu thanh toán
    private String amount;         // Số tiền giao dịch
    private String orderInfo;      // Thông tin đơn hàng
    private String orderType;      // Loại đơn hàng (ví dụ: momo_wallet)
    private String transId;        // Mã giao dịch từ MoMo
    private int resultCode;        // Mã kết quả giao dịch (0 = thành công)
    private String message;        // Mô tả trạng thái giao dịch
    private long responseTime;     // Thời gian xử lý giao dịch (Unix timestamp)
    private String extraData;      // Thông tin bổ sung (nếu có)
    private String payType;        // Loại thanh toán (app, qr, ...)
    private String signature;      // Chữ ký dùng để xác thực thông tin
    private String accessKey;      // Access Key
}
