package com.booksms.authentication.interfaceLayer.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotNull(message = "id is required for reset password")
    private Integer id;
    @NotNull(message = "New password is required for reset password")
    private String password;
}
