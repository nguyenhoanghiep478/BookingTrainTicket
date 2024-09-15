package com.bookms.order.application.servicegateway;

import com.bookms.order.application.model.CustomerModel;

public interface IAuthServiceGateway {
    CustomerModel findCustomerById(int customerId);
}
