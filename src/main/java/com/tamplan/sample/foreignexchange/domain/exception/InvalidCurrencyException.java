package com.tamplan.sample.foreignexchange.domain.exception;

import com.tamplan.sample.foreignexchange.domain.ErrorCode;
import com.tamplan.sample.foreignexchange.infra.spring.exception.ApplicationException;

public class InvalidCurrencyException extends ApplicationException {
    public InvalidCurrencyException(String message) {
        super(ErrorCode.INVALID_CURRENCY_CODE, message);
    }

}
