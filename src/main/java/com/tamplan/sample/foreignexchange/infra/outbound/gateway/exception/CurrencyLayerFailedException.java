package com.tamplan.sample.foreignexchange.infra.outbound.gateway.exception;

import com.tamplan.sample.foreignexchange.domain.common.ErrorCode;
import com.tamplan.sample.foreignexchange.domain.exception.ApplicationException;

public class CurrencyLayerFailedException extends ApplicationException {
    public CurrencyLayerFailedException() {
        super(ErrorCode.CURRENCY_LAYER_FAILED);
    }

    public CurrencyLayerFailedException(String message) {
        super(ErrorCode.CURRENCY_LAYER_FAILED, message);
    }

}
