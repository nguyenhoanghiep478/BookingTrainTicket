package com.booksms.marketing.interfaceLayer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
