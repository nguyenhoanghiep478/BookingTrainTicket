package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.model.BookModel;
import com.bookms.order.core.domain.Entity.Orders;
import com.bookms.order.core.domain.Exception.BookNotInStockException;
import com.bookms.order.core.domain.Exception.CreateFailedException;
import com.bookms.order.core.domain.Exception.InSufficientQuantityException;
import com.bookms.order.core.domain.Repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateSellOrderUseCase {
    private final IOrderRepository orderRepository;
    public Orders execute(Orders orders, List<BookModel> bookModels){
        for(int i =0 ;i<bookModels.size();i++){
            if(!bookModels.get(i).getIsInStock()){
                throw new BookNotInStockException(String.format("book with name %s not in stock",bookModels.get(i).getName()));
            }
            if(orders.getOrderItems().get(i).getTotalQuantity() > bookModels.get(i).getAvailableQuantity() ){
                throw new InSufficientQuantityException(String.format("%s not enough %s quantity to sell ",bookModels.get(i).getTitle(),orders.getOrderItems().get(i).getTotalQuantity()));
            }
        }
        Orders result =  orderRepository.save(orders);
        if(result == null){
            throw new CreateFailedException("create order failed");
        }
        return result;
    }

}
