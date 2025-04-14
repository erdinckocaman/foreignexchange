package com.tamplan.sample.foreignexchange.infra.spring.response;

import com.tamplan.sample.foreignexchange.infra.spring.exception.ApplicationException;

public record ForeignExchangeResponse(String exceptionId, String code) {

    public static ForeignExchangeResponse of(ApplicationException applicationException) {
        return new ForeignExchangeResponse(applicationException.getExceptionId(), applicationException.getCode());
    }
}
