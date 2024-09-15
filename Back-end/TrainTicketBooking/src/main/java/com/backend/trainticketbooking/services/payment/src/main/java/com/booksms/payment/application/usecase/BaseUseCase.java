package com.booksms.payment.application.usecase;

public interface BaseUseCase<Entity,Input>{
    Entity execute(Input input);
}
