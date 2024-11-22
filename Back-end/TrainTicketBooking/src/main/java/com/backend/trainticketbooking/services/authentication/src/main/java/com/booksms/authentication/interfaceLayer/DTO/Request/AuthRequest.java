package com.booksms.authentication.interfaceLayer.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @Email(message = "email must have @ and domain after")
    private String email;

    @NotNull(message = "password must not null")
    private String password;

}
