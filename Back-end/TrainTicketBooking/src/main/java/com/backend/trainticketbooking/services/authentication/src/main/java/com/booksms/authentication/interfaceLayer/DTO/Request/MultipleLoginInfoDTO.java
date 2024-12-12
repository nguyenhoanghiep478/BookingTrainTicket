package com.booksms.authentication.interfaceLayer.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultipleLoginInfoDTO {
    private String accessToken;
    private String refreshToken;
    private String userName;
}
