package com.backend.store.interfacelayer.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ResponseDTO {
    private List<String> message;
    private int status;
    private Object result;
}
