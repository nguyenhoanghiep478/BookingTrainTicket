package com.bookms.order.core.domain.Exception;

public class InSufficientQuantityException extends RuntimeException{
    public InSufficientQuantityException(String message){
        super(message);
    }
}
