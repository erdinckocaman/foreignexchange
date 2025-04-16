package com.tamplan.sample.foreignexchange.domain.exception;

import com.tamplan.sample.foreignexchange.domain.common.ErrorCode;

public class InvalidAmountException extends InvalidUserInputException {

    public InvalidAmountException(String message) {
        super(ErrorCode.INVALID_AMOUNT, message);
    }

}
