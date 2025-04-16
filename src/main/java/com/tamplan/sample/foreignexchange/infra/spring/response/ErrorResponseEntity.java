package com.tamplan.sample.foreignexchange.infra.spring.response;

import com.tamplan.sample.foreignexchange.domain.exception.ApplicationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class ErrorResponseEntity extends ResponseEntity<ErrorResponse> {

    public ErrorResponseEntity(ApplicationException applicationException, HttpStatusCode status) {
        super(ErrorResponse.of(applicationException), buildErrorHeader(applicationException.getExceptionId()), status);
    }

    private static MultiValueMap<String, String> buildErrorHeader(String exceptionId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("X-Exception-Id", List.of(exceptionId));
        return httpHeaders;
    }
}
