package com.bookms.order.interfaceLayer.service.impl;

import com.bookms.order.application.model.OrderItemModel;
import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.model.PaymentModel;
import com.bookms.order.application.usecase.impl.CreateOrderUseCase;
import com.bookms.order.application.usecase.impl.PreCreateOrderUseCase;
import com.bookms.order.core.domain.Exception.InvalidToken;
import com.bookms.order.core.domain.State.PaymentMethod;
import com.bookms.order.infrastructure.serviceGateway.impl.MarketingServiceGateway;
import com.bookms.order.interfaceLayer.DTO.OrderDTO;
import com.bookms.order.interfaceLayer.DTO.Request.TicketDTO;
import com.bookms.order.interfaceLayer.DTO.ResponseOrderCreated;
import com.bookms.order.interfaceLayer.DTO.ResponsePayment;
import com.bookms.order.interfaceLayer.service.ICreateOrderService;
import com.bookms.order.interfaceLayer.service.IUpdateOrderService;
import com.bookms.order.interfaceLayer.service.redis.OrderRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.bookms.order.core.domain.Entity.Status.*;
import static com.bookms.order.core.domain.State.Concurency.USD;
import static com.bookms.order.core.domain.State.StaticPayment.MOMO;
import static com.bookms.order.core.domain.State.StaticPayment.PAYPAL;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateOrderService implements ICreateOrderService {
    private final CreateOrderUseCase createOrderUseCase;
    private final OrderRedisService orderRedisService;
    private final PreCreateOrderUseCase preCreateOrderUseCase;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, TicketDTO> storeKafkaTemplate;
    private final KafkaTemplate<String,OrdersModel> paymentKafkaTemplate;
    private final MarketingServiceGateway marketingServiceGateway;

    @Override
    public OrdersModel createOrder(OrdersModel request) {
        log.info(request.toString());
        OrdersModel ordersModel = createOrderUseCase.execute(request);
        ordersModel.setCustomerEmail(request.getCustomerEmail());
        if(request.getCustomerId().equals(0)){
            ordersModel.setCustomerId(null);
        }
        BigDecimal totalPrice = ordersModel.getTotalPrice();
        List<OrderItemModel> orderItems = request.getOrderItems();
        int size = 0;
        if(ordersModel.getIsHaveRoundTrip()){
            size = request.getRoundTripItems().size();
            totalPrice = totalPrice.subtract(request.getRoundTripTotalPrice());
        }else{
            size = request.getOrderItems().size();
        }
        List<Integer> forwardSeatIds = new ArrayList<>();
        for(int i = 0; i < size; i++){
            Integer seatId = orderItems.get(i).getSeatId();
            forwardSeatIds.add(seatId);
        }

        TicketDTO ticketDTO = TicketDTO.builder()
                .id(ordersModel.getTicketId())
                .customerEmail(ordersModel.getCustomerEmail())
                .customerName(ordersModel.getCustomerName())
                .scheduleId(ordersModel.getScheduleId())
                .arrivalStationId(ordersModel.getArrivalStationId())
                .departureStationId(ordersModel.getDepartureStationId())
                .seatIds(forwardSeatIds)
                .orderNumber(request.getOrderNumber())
                .customerId(ordersModel.getCustomerId())
                .price(totalPrice)
                .build();
        log.info(ticketDTO.toString());
        storeKafkaTemplate.send("order-created", ticketDTO);

        if(request.getIsHaveRoundTrip()){
            ticketDTO.setId(ordersModel.getRoundTripTicketId());
            ticketDTO.setScheduleId(ordersModel.getRoundTripScheduleId());
            ticketDTO.setDepartureStationId(ordersModel.getArrivalStationId());
            ticketDTO.setArrivalStationId(ordersModel.getDepartureStationId());
            ticketDTO.setPrice(ordersModel.getRoundTripTotalPrice());
            ticketDTO.setSeatIds(ordersModel.getRoundTripItems().stream().map(OrderItemModel::getSeatId).toList());
            storeKafkaTemplate.send("order-created", ticketDTO);
        }
        return ordersModel;
    }
    @KafkaListener(id = "consumer-created-order-response",topics = "order-created-response")
    public void preCreate(ResponseOrderCreated orderCreated) {
        log.info("{} message: {}", orderCreated.getServiceName(), orderCreated.getMessage());

    }

    @Override
    public PaymentModel prePayment(OrderDTO request) {
        OrdersModel ordersModel = null;
        if(request.getToken() == null || request.getToken().equals("0") ){
             ordersModel =  preCreateOrderUseCase.execute(modelMapper.map(request,OrdersModel.class));
             ordersModel.setCustomerEmail(request.getCustomerEmail());
             ordersModel.setCustomerName(request.getCustomerName());
             orderRedisService.saveOrder(ordersModel);
             paymentKafkaTemplate.send("pre-create-order",ordersModel);
             return null;
        }

        if(!marketingServiceGateway.validateToken(Long.valueOf(request.getToken()))){
            throw new InvalidToken("invalid token");
        }

        ordersModel = orderRedisService.getOrder(request.getOrderNumber());
        createOrderUseCase.execute(ordersModel);
        if(ordersModel.getIsHaveRoundTrip()){
            ordersModel.setOrderNumber(ordersModel.getOrderNumber()-1);
        }else{
            ordersModel.setOrderNumber(ordersModel.getOrderNumber());
        }

        return getPaymentModel(ordersModel);
    }

    @Override
    public OrdersModel afterPayment(Long orderNumber) {
        return orderRedisService.getOrder(orderNumber);
    }

    @Override
    public void completeOrder(Long key) {
        orderRedisService.removeOrder(key);
    }

    @KafkaListener(id = "payment-response",topics = "payment-response")
    public void handleResponse(ResponsePayment responsePayment) {
        log.info(responsePayment.toString());
        OrdersModel orderWasPaid= afterPayment(responsePayment.getOrderNumber());
        orderWasPaid.setPaymentMethod(PaymentMethod.valueOf(responsePayment.getPaymentMethod()));
        orderWasPaid.setPaymentId(responsePayment.getPaymentId());
        orderWasPaid.setStatus(responsePayment.getStatus());
        createOrder(orderWasPaid);
    }


    @Override
    public OrdersModel handleOrderWasPaid(ResponsePayment responsePayment) {
        OrdersModel orderWasPaid= afterPayment(responsePayment.getOrderNumber());
        orderWasPaid.setPaymentMethod(PaymentMethod.valueOf(responsePayment.getPaymentMethod()));
        orderWasPaid.setPaymentId(responsePayment.getPaymentId());
        orderWasPaid.setStatus(responsePayment.getStatus());
        return orderWasPaid;
    }

    @Override
    public OrdersModel handleCodOrder(OrdersModel model) {
        OrdersModel preCreateOrder = preCreateOrderUseCase.execute(model);
        preCreateOrder.setPaymentMethod(PaymentMethod.valueOf(PaymentMethod.COD.getValue()));
        preCreateOrder.setStatus(PROCESSING);
        return createOrder(preCreateOrder);
    }


    private PaymentModel getPaymentModel(OrdersModel ordersModel) {
        String description = null;
        BigDecimal priceByMethod = ordersModel.getTotalPrice();
        if(ordersModel.getPaymentMethod().equals(PaymentMethod.PAYPAL)){
            priceByMethod = priceByMethod.divide(BigDecimal.valueOf(24000), RoundingMode.HALF_UP);
            description = PAYPAL.description;
        }else if(ordersModel.getPaymentMethod().equals(PaymentMethod.MOMO)){
            description = MOMO.description;
        }
        return PaymentModel.builder()
                .orderNumber(ordersModel.getOrderNumber())
                .customerId(ordersModel.getCustomerId())
                .total(priceByMethod.doubleValue())
                .currency(USD.getConcurency())
                .description(description)
                .intent(PAYPAL.intent)
                .method(String.valueOf(ordersModel.getPaymentMethod()))
                .build();
    }


}
