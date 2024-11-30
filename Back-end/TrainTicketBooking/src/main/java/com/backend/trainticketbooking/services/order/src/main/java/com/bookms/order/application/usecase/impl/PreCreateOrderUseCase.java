package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.application.model.OrderItemModel;
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
import java.math.RoundingMode;
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
        orders.setSeatModels(seatModels);
        if(orders.getIsHaveRoundTrip()){
            List<OrderItemModel> forwardItems = orders.getOrderItems();
            forwardItems.addAll(orders.getRoundTripItems());
            orders.setOrderItems(forwardItems);
        }
        BigDecimal totalPrice =  validOrder(orders,seatModels);
        orders.setTotalPrice(totalPrice);
        Random random = new Random();
        orders.setTicketId(Math.abs(random.nextInt()));
        orders.setRoundTripTicketId(Math.abs(random.nextInt()));



        for(int i = 0 ; i < seatModels.size() ; i++){
            orders.getOrderItems().get(i).setName(seatModels.get(i).getSeatNumber());
        }

        orders.setSeatModels(seatModels);
        orders.setStatus(Status.PENDING);
        return orders;
    }



    private BigDecimal validOrder(OrdersModel order, List<SeatModel> seatModels){
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal roundTripPrice = BigDecimal.ZERO;
        if(order.getIsHaveRoundTrip()){
            int forwardSize = order.getSeatModels().size()/2;
            for(int i = 0 ; i< order.getRoundTripItems().size();i++){
                BigDecimal price = seatModels.get(i+forwardSize).getPrice().stripTrailingZeros();
                price = price.setScale(2, RoundingMode.HALF_UP);
                BigDecimal orderPrice = order.getRoundTripItems().get(i).getPrice().stripTrailingZeros();
                orderPrice = orderPrice.setScale(2, RoundingMode.HALF_UP);
                if(!price.equals(orderPrice)){
                    throw new PriceNotTheSameException(String.format("price for item %s not the same with store",seatModels.get(i+forwardSize).getSeatNumber()));
                }
                roundTripPrice = roundTripPrice.add(price);
            }
            order.setRoundTripTotalPrice(roundTripPrice);
        }

        for(int i = 0 ; i<order.getOrderItems().size()/2 ; i++){
            BigDecimal price = seatModels.get(i).getPrice().stripTrailingZeros();
            price = price.setScale(2, RoundingMode.HALF_UP);
            BigDecimal orderPrice = order.getOrderItems().get(i).getPrice().stripTrailingZeros();
            orderPrice = orderPrice.setScale(2, RoundingMode.HALF_UP);
            if(!price.equals(orderPrice) ){
                throw new PriceNotTheSameException(String.format("price for item %s not the same with store",seatModels.get(i).getSeatNumber()));
            }
            totalPrice = totalPrice.add(price);

        }

        if(order.getTotalPrice()!=null){
            totalPrice = totalPrice.stripTrailingZeros();
            roundTripPrice = roundTripPrice.stripTrailingZeros();
            totalPrice = totalPrice.add(roundTripPrice);

            totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
            BigDecimal orderTotalPrice = order.getTotalPrice();
            orderTotalPrice = orderTotalPrice.setScale(2, RoundingMode.HALF_UP);
            order.setTotalPrice(orderTotalPrice);
            if(!totalPrice.equals(order.getTotalPrice())){
                throw new TotalPriceNotTheSameException(String.format("total price for order %s not correct ",order.getOrderNumber()));
            }
        }


        return totalPrice;
    }


}
