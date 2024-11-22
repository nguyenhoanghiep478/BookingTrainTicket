package com.bookms.order.core.domain.Exception;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionDTO {
    String errorDescription;
    int code;
    String error;
    Set<String> validationErrors;

}
