package com.booksms.authentication.application;

public interface BaseUsecase<Entity,Model>{

    Model execute(Entity model);
}
