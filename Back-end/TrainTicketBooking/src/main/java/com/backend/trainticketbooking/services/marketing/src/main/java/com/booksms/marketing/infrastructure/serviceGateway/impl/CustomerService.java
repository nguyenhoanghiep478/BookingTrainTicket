package com.booksms.marketing.infrastructure.serviceGateway.impl;

import com.booksms.marketing.application.model.CustomerModel;
import com.booksms.marketing.infrastructure.feignClient.CustomerClient;
import com.booksms.marketing.infrastructure.serviceGateway.ICustomerService;
import com.booksms.marketing.interfaceLayer.dto.response.ResponseDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerClient customerClient;
    private final ModelMapper modelMapper;

    @Override
    public CustomerModel getCustomerById(int customerId) {
        ResponseEntity<ResponseDTO> response = customerClient.getUserById(customerId);
        ResponseDTO responseDTO = response.getBody();
        CustomerModel customerModel = modelMapper.map(responseDTO.getResult(),CustomerModel.class);
        return customerModel;
    }


}
