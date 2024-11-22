package com.backend.store.infrastructure.servicegateway.impl;

import com.backend.store.core.domain.exception.CustomerNotFoundException;
import com.backend.store.infrastructure.FeignClient.AuthenticationClient;
import com.backend.store.infrastructure.servicegateway.IAuthenticationService;
import com.backend.store.interfacelayer.dto.ResponseDTO;
import com.backend.store.interfacelayer.dto.objectDTO.CustomerDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final AuthenticationClient authenticationClient;
    private final ModelMapper modelMapper;


    @Override
    public CustomerDTO getCustomerById(Integer customerId) {
        ResponseEntity<ResponseDTO> responseEntity = authenticationClient.getById(customerId);
        ResponseDTO responseDTO = responseEntity.getBody();
        if(responseDTO.getResult() == null){
            throw new CustomerNotFoundException(String.format("Customer with id %s not found", customerId));
        }
        return modelMapper.map(responseDTO, CustomerDTO.class);
    }
}
