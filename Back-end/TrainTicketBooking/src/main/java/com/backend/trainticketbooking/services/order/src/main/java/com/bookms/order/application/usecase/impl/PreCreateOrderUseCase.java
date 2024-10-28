package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.model.SeatModel;
import com.bookms.order.core.domain.Entity.Order;
import com.bookms.order.core.domain.Entity.Status;
import com.bookms.order.core.domain.Exception.OrderExistException;
import com.bookms.order.core.domain.Exception.PriceNotTheSameException;
import com.bookms.order.core.domain.Exception.TotalPriceNotTheSameException;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import com.bookms.order.infrastructure.serviceGateway.IStoreServiceGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class PreCreateOrderUseCase implements BaseUseCase<OrdersModel, OrdersModel>{
    private final IOrderRepository orderRepository;
    private final IStoreServiceGateway serviceGateway;


    @Override
    public OrdersModel execute(OrdersModel orders) {
        if(orders.getOrderNumber() != null){
            Optional<Order> order = orderRepository.findByOrderNumber(orders.getOrderNumber());
            if(order.isPresent()){
                throw new OrderExistException("order number already exist");
            }
        }

        List<SeatModel> seatModels = serviceGateway.getSeatsAvailableInSystem(orders);

        BigDecimal totalPrice =  validOrder(orders,seatModels);
        orders.setTotalPrice(totalPrice);
        Random random = new Random();
        orders.setTicketId(Math.abs(random.nextInt()));


        for(int i = 0 ; i < seatModels.size() ; i++){
            orders.getOrderItems().get(i).setName(seatModels.get(i).getSeatNumber());
        }

        orders.setSeatModels(seatModels);
        orders.setStatus(Status.PENDING);
        return orders;
    }



    private BigDecimal validOrder(OrdersModel order, List<SeatModel> seatModels){
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(int i = 0 ; i<order.getOrderItems().size() ; i++){
            BigDecimal price = seatModels.get(i).getPrice().stripTrailingZeros();
            BigDecimal orderPrice = order.getOrderItems().get(i).getPrice().stripTrailingZeros();
            if(!price.equals(orderPrice) ){
                throw new PriceNotTheSameException(String.format("price for item %s not the same with store",seatModels.get(i).getSeatNumber()));
            }
            totalPrice = totalPrice.add(price);

        }
        if(order.getTotalPrice()!=null){
            totalPrice = totalPrice.stripTrailingZeros();
            order.setTotalPrice(order.getTotalPrice().stripTrailingZeros());
            if(!totalPrice.equals(order.getTotalPrice())){
                throw new TotalPriceNotTheSameException(String.format("total price for order %s not correct ",order.getOrderNumber()));
            }
        }
        return totalPrice;
    }


}
