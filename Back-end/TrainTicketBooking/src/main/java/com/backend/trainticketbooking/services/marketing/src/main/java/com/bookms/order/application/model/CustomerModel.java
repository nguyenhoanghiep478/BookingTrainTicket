package com.bookms.order.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
