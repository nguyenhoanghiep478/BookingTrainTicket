package com.booksms.authentication.interfaceLayer.DTO.Request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    @Email(message = "Not valid email format")
    private String email;
    private String phone;
    private String address;
    @NotNull(message = "password is required")
    private String password;
}
