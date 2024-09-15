package com.bookms.order.application;

import jakarta.annotation.Nullable;

public interface BaseUseCase <Entity,Input>{
    Entity execute(Input input);
}
