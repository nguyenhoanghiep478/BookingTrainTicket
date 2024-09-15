package com.bookms.order.core.domain.Exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ExceptionDTO {
    String errorDescription;
    int code;
    String error;
    Set<String> validationErrors;

}
