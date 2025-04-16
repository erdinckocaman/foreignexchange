package com.tamplan.sample.foreignexchange.domain.exception;

public final class UndefinedException extends ApplicationException{

    public UndefinedException(Exception innerException) {
        super(innerException);
    }
}
