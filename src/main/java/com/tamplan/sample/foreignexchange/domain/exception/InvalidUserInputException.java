package com.tamplan.sample.foreignexchange.domain.exception;

import com.tamplan.sample.foreignexchange.domain.common.ErrorCode;

public class InvalidUserInputException extends ApplicationException {

    public InvalidUserInputException(String message) {
        super(ErrorCode.INVALID_USER_INPUT, message);
    }

    public InvalidUserInputException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    @Override
    public int getHttpStatus() {
        return 400;
    }
}
