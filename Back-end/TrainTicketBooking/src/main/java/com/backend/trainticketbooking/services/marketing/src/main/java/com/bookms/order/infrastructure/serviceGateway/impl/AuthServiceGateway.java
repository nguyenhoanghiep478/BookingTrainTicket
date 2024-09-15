package com.bookms.order.infrastructure.serviceGateway.impl;

import com.bookms.order.application.model.CustomerModel;
import com.bookms.order.application.servicegateway.IAuthServiceGateway;
import com.bookms.order.infrastructure.FeignClient.CustomerClient;
import com.bookms.order.interfaceLayer.DTO.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthServiceGateway implements IAuthServiceGateway {
    private final CustomerClient customerClient;
    private final ModelMapper modelMapper;

    @Override
    public CustomerModel findCustomerById(int customerId) {
        try{
            ResponseEntity<ResponseDTO> response = customerClient.getUserById(customerId);
            log.info(response.getBody().toString());
            ResponseDTO responseDTO = response.getBody();
            if(responseDTO != null) {
                return modelMapper.map(responseDTO.getResult(), CustomerModel.class);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return null;
    }
}
