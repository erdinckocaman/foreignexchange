package com.tamplan.sample.foreignexchange.domain;

public enum ErrorCode {
    INVALID_CURRENCY_CODE("FE_001"),
    INVALID_AMOUNT("FE_002" );

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
