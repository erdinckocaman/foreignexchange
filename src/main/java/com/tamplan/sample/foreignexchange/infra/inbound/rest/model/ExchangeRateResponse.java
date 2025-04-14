package com.tamplan.sample.foreignexchange.infra.inbound.rest.model;

import java.math.BigDecimal;

public record ExchangeRateResponse(BigDecimal exchangeRate) {
    public static ExchangeRateResponse of(BigDecimal exchangeRate) {
        return new ExchangeRateResponse(exchangeRate);
    }
}
