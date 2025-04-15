package com.tamplan.sample.foreignexchange.infra.spring.response;

import com.tamplan.sample.foreignexchange.infra.spring.exception.ApplicationException;

public record ForeignExchangeResponse(String exceptionId, String code, String message) {

    public static ForeignExchangeResponse of(ApplicationException applicationException) {
        var message = "";
        if (applicationException.isUserError()) {
            message = applicationException.getUserMessage();
        }
        return new ForeignExchangeResponse(
                applicationException.getExceptionId(), applicationException.getCode(), message);
    }
}
