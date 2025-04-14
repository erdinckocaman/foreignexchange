package com.tamplan.sample.foreignexchange.infra.spring.exception;

public final class UndefinedException extends ApplicationException{

    public UndefinedException(Exception innerException) {
        super(innerException);
    }
}
