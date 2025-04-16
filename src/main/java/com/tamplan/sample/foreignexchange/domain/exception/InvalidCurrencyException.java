package com.tamplan.sample.foreignexchange.domain.exception;

import com.tamplan.sample.foreignexchange.domain.common.ErrorCode;

public class InvalidCurrencyException extends InvalidUserInputException {

    public InvalidCurrencyException(String message) {
        super(ErrorCode.INVALID_CURRENCY_CODE, message);
    }

}
