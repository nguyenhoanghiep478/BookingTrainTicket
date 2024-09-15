package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.core.domain.Entity.OrderType;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.core.domain.Entity.Status;
import com.bookms.order.core.domain.Exception.OrderExistException;
import com.bookms.order.core.domain.State.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
        Orders result = null;
        if(orderModel.getOrderType() == OrderType.SELL && orderModel.getStatus() != Status.PENDING && !orderModel.getPaymentMethod().equals(PaymentMethod.COD.getValue()) ) {
            result = updateOrderUseCase.execute(orderModel);
            return orderModel;
        }
        Orders orders = orderMapperUseCase.toOrders(orderModel);

        if(orders.getOrderType() == OrderType.BUY && orders.getPaymentId() == null){
            result = createBuyOrderUseCase.execute(orders);
        }
        else{
            result = createSellOrderUseCase.execute(orders, orderModel.getBookModels());
        }

        return orderModel;
    }




}
