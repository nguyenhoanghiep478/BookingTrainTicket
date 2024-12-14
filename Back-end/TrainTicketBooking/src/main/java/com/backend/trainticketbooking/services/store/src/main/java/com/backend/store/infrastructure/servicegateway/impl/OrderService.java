package com.backend.store.infrastructure.servicegateway.impl;

import com.backend.store.infrastructure.FeignClient.OrderClient;
import com.backend.store.infrastructure.servicegateway.IOrderService;
import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.OrderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderClient orderClient;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Override
    public List<OrderDTO> findByOrderNumber(Long orderNumber) {
        ResponseEntity<ResponseDTO> responseEntity = orderClient.getByOrderNumber(orderNumber);
        ResponseDTO responseDTO = responseEntity.getBody();
        assert responseDTO != null;
        if(responseDTO.getResult() == null){
            return null;
        }
        List<OrderDTO> response = null;
        response = modelMapper.map(responseDTO.getResult(), List.class);

        // Sử dụng ObjectMapper để ánh xạ chính xác kiểu dữ liệu
        response = objectMapper.convertValue(
                response,
                objectMapper.getTypeFactory().constructCollectionType(List.class, OrderDTO.class)
        );
        return response;
    }
}
