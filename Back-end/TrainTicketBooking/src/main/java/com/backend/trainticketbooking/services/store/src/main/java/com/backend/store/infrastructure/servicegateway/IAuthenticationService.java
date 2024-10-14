package com.backend.store.infrastructure.servicegateway;

import com.backend.store.interfacelayer.dto.objectDTO.CustomerDTO;

public interface IAuthenticationService {
    CustomerDTO getCustomerById(Integer customerId);
}
