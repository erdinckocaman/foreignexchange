package com.tamplan.sample.foreignexchange.infra.spring.response;

import com.tamplan.sample.foreignexchange.infra.spring.exception.ApplicationException;

public record ErrorResponse(String exceptionId, String code, String message) {

    public static ErrorResponse of(ApplicationException applicationException) {
        var message = "";
        if (applicationException.isUserError()) {
            message = applicationException.getUserMessage();
        }
        return new ErrorResponse(
                applicationException.getExceptionId(), applicationException.getCode(), message);
    }
}
