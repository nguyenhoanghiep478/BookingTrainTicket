package com.booksms.authentication.interfaceLayer.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.web.header.Header;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private List<String> message;
    private int status;
    private Object result;
}
