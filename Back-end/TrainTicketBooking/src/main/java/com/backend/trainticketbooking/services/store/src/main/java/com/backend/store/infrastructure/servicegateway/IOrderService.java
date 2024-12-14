package com.backend.store.infrastructure.servicegateway;

import com.backend.store.interfacelayer.dto.objectDTO.OrderDTO;

import java.util.List;

public interface IOrderService {
    List<OrderDTO> findByOrderNumber(Long orderNumber);
}
