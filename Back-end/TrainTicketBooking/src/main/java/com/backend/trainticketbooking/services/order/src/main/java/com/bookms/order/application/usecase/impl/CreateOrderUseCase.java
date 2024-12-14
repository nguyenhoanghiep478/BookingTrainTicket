package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.core.domain.Entity.Order;
import com.bookms.order.core.domain.Entity.Status;
import com.bookms.order.core.domain.Exception.OrderExistException;
import com.bookms.order.core.domain.State.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class CreateOrderUseCase implements BaseUseCase<OrdersModel,OrdersModel>{
    private final CreateBuyOrderUseCase createBuyOrderUseCase;
    private final CreateSellOrderUseCase createSellOrderUseCase;
    private final UpdateOrderUseCase updateOrderUseCase;
    private final OrderMapperUseCase orderMapperUseCase;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrdersModel execute(OrdersModel orderModel) throws OrderExistException {
        Order result = null;
        if( orderModel.getStatus() != Status.PENDING && !orderModel.getPaymentMethod().equals(PaymentMethod.COD.getValue()) ) {
            result = updateOrderUseCase.execute(orderModel);
            return orderModel;
        }
        if(orderModel.getIsHaveRoundTrip()){
            List<Order> orders = orderMapperUseCase.toOrdersWithRoundTrip(orderModel);
            Order forwardOrder = orders.get(0);
            forwardOrder.setIsHaveRoundTrip(true);
            Order roundTripOrder = orders.get(1);
            orderModel.setForwardSeatIds(getForwardTripSeatIds(orderModel));
            orderModel.setRoundTripSeatIds(getRoundTripSeatIds(orderModel));
            createSellOrderUseCase.execute(forwardOrder);
            createSellOrderUseCase.execute(roundTripOrder);
        }else{
            Order order = orderMapperUseCase.toOrders(orderModel);

            if(order.getPaymentId() == null){
                result = createBuyOrderUseCase.execute(order);
            }
            else{
                result = createSellOrderUseCase.execute(order);
            }

        }

        return orderModel;
    }

    private List<Integer> getRoundTripSeatIds(OrdersModel ordersModel) {
        List<Integer> roundTripSeatIds = new ArrayList<>();
        int seatSize = ordersModel.getSeatModels().size();
        for(int i =seatSize/2 ;i<seatSize ; i++){
            roundTripSeatIds.add(ordersModel.getSeatModels().get(i).getId());
        }
        return roundTripSeatIds;
    }

    private List<Integer> getForwardTripSeatIds(OrdersModel ordersModel) {
        List<Integer> forwardTripSeatIds = new ArrayList<>();
        int seatSize = ordersModel.getSeatModels().size();
        for(int i =0 ;i<seatSize/2 ; i++){
            forwardTripSeatIds.add(ordersModel.getSeatModels().get(i).getId());
        }
        return forwardTripSeatIds;
    }
}
