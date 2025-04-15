package com.tamplan.sample.foreignexchange.infra.outbound.gateway.exception;

import com.tamplan.sample.foreignexchange.domain.ErrorCode;
import com.tamplan.sample.foreignexchange.infra.spring.exception.ApplicationException;

public class CurrencyLayerFailedException extends ApplicationException {
    public CurrencyLayerFailedException() {
        super(ErrorCode.CURRENCY_LAYER_FAILED);
    }

    public CurrencyLayerFailedException(String message) {
        super(ErrorCode.CURRENCY_LAYER_FAILED, message);
    }

}
