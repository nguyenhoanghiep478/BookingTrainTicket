package com.booksms.authentication.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer address;
    private String password;
    private Boolean isVerified;
    private Boolean isFirstVisit;

    private String oldPassword;
}
