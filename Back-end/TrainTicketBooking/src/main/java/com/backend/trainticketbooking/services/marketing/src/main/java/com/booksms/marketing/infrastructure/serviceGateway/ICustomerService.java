package com.booksms.marketing.infrastructure.serviceGateway;

import com.booksms.marketing.application.model.CustomerModel;

public interface ICustomerService {
    CustomerModel getCustomerById(int customerId);
}
