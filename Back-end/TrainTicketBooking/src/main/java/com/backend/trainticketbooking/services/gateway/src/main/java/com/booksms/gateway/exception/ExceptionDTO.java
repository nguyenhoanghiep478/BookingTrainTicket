package com.booksms.gateway.exception;

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
}
