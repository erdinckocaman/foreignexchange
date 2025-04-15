package com.tamplan.sample.foreignexchange.domain.exception;

import com.tamplan.sample.foreignexchange.domain.ErrorCode;
import com.tamplan.sample.foreignexchange.infra.spring.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class InvalidUserInputException extends ApplicationException {

    public InvalidUserInputException(String message) {
        super(ErrorCode.INVALID_USER_INPUT, message);
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
