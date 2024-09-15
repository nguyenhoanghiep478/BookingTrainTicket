package com.booksms.authentication.interfaceLayer.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private Integer id;
    @NotNull(message = "first name is required")
    @NotEmpty(message = "first name is required")
    private String firstName;
    @NotNull(message = "last name is required")
    @NotEmpty(message = "last name is required")
    private String lastName;
    @Email(message = "Not valid email format")
    @NotNull(message = "email is required")
    @NotEmpty(message = "email is required")
    private String email;
    @Pattern(regexp = "^0[0-9]{9}$", message = "phone number must start by 0 and have 10 number")
    private String phone;
    private String address;
    @NotNull(message = "password is required")
    @NotEmpty(message = "password is required")
    private String password;
}
