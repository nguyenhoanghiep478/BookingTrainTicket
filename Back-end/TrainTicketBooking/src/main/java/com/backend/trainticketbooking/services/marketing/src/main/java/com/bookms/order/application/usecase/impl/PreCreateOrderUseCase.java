package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.BaseUseCase;
import com.bookms.order.application.model.BookModel;
import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.core.domain.Exception.OrderExistException;
import com.bookms.order.core.domain.Exception.PriceNotTheSameException;
import com.bookms.order.core.domain.Exception.TotalPriceNotTheSameException;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PreCreateOrderUseCase implements BaseUseCase<OrdersModel, OrdersModel>{
    private final IOrderRepository orderRepository;
    private final FindBookUtils findBookUtils;

    @Override
    public OrdersModel execute(OrdersModel orders) {
        orders.setOrderItems(findBookUtils.toSet(orders.getOrderItems()));
        if(orders.getOrderNumber() != null){
            Optional<Orders> order = orderRepository.findByOrderNumber(orders.getOrderNumber());
            if(order.isPresent()){
                throw new OrderExistException("order number already exist");
            }
        }

        List<BookModel> bookModels = findBookUtils.getBookModels(orders);

        BigDecimal totalPrice =  validOrder(orders,bookModels);
        orders.setTotalPrice(totalPrice);



        for(int i = 0 ; i < bookModels.size() ; i++){
            orders.getOrderItems().get(i).setName(bookModels.get(i).getName());
        }

        orders.setBookModels(bookModels);
        return orders;
    }



    private BigDecimal validOrder(OrdersModel order, List<BookModel> bookModels){
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(int i = 0 ; i<order.getOrderItems().size() ; i++){
            BigDecimal price = bookModels.get(i).getPrice().stripTrailingZeros();
            BigDecimal orderPrice = order.getOrderItems().get(i).getPrice().stripTrailingZeros();
            if(!price.equals(orderPrice) ){
                throw new PriceNotTheSameException(String.format("price for item %s not the same with store",bookModels.get(i).getName()));
            }
            totalPrice = totalPrice.add(price.multiply(BigDecimal.valueOf(order.getOrderItems().get(i).getTotalQuantity())));

        }
        if(order.getTotalPrice()!=null){
            totalPrice = totalPrice.add(BigDecimal.valueOf(order.getShipmentFee()));
            totalPrice = totalPrice.stripTrailingZeros();
            order.setTotalPrice(order.getTotalPrice().stripTrailingZeros());
            if(!totalPrice.equals(order.getTotalPrice())){
                throw new TotalPriceNotTheSameException(String.format("total price for order %s not correct ",order.getOrderNumber()));
            }
        }
        return totalPrice;
    }


}
