package com.bookms.order.core.domain.Exception;

public class OrderExistException extends RuntimeException{
    public OrderExistException(String message){
        super(message);
    }
}
