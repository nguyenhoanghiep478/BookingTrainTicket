package com.bookms.order.application.usecase.impl;

import com.bookms.order.application.model.BookModel;
import com.bookms.order.application.model.OrderItemModel;
import com.bookms.order.application.model.OrdersModel;
import com.bookms.order.application.servicegateway.IBookServiceGateway;
import com.bookms.order.core.domain.Exception.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FindBookUtils {
    private final IBookServiceGateway bookServiceGateway;

    public List<BookModel> getBookModels(OrdersModel orders){
        Set<Integer> booksId = orders.getOrderItems().stream().map(OrderItemModel::getBookId).collect(Collectors.toCollection(LinkedHashSet::new));

        List<BookModel> result = bookServiceGateway.findAllBookWithListId(booksId);
        if(result.isEmpty()){
            throw new BookNotFoundException(String.format("books in order not found"));
        }
        return result;
    }

    public List<OrderItemModel> toSet(List<OrderItemModel> orderItems){
        List<OrderItemModel> result = new ArrayList<>();
        Hashtable<Integer,Integer> setBookId = new Hashtable<>();
        for(int i = 0 ; i < orderItems.size() ; i++){
            if(!setBookId.containsKey(orderItems.get(i).getBookId())){
                setBookId.put(orderItems.get(i).getBookId(),i);
                result.add(orderItems.get(i));
            }else{
                int indexDuplicate = setBookId.get(orderItems.get(i).getBookId());
                int currentQuantity = result.get(indexDuplicate).getTotalQuantity();
                int newQuantity = orderItems.get(i).getTotalQuantity() + currentQuantity;
                result.get(setBookId.get(orderItems.get(indexDuplicate).getBookId())).setTotalQuantity(newQuantity);
            }
        }
        return result;
    }
}
