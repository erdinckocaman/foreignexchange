package com.tamplan.sample.foreignexchange.domain.exception;

import com.tamplan.sample.foreignexchange.domain.ErrorCode;
import com.tamplan.sample.foreignexchange.infra.spring.exception.ApplicationException;

public class InvalidAmountException extends ApplicationException {

    public InvalidAmountException(String message) {
        super(ErrorCode.INVALID_AMOUNT, message);
    }
}
