package com.tamplan.sample.foreignexchange.domain.common;

public enum ErrorCode {
    INVALID_CURRENCY_CODE("FE_001"),
    INVALID_AMOUNT("FE_002" ),
    CURRENCY_LAYER_FAILED("FE_101"),
    INVALID_USER_INPUT("FE_201");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
