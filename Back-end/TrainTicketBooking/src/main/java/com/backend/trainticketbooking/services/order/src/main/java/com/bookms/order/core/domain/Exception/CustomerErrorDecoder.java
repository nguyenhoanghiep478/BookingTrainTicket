package com.bookms.order.core.domain.Exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.bouncycastle.util.StoreException;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class CustomerErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ExceptionDTO exceptionDTO = mapper.readValue(response.body().asInputStream(),ExceptionDTO.class);
            return new ServiceException(exceptionDTO.getErrorDescription(),
                    HttpStatus.BAD_REQUEST,
                    exceptionDTO.code,
                    exceptionDTO.error,
                    exceptionDTO.validationErrors);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
