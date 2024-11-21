package com.booksms.authentication.interfaceLayer.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteUserRequest {
    private String username;
    private String password;
    private Integer deleteUserId;
}
