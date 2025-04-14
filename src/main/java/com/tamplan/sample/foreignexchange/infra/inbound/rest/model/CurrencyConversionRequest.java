package com.tamplan.sample.foreignexchange.infra.inbound.rest.model;

import java.math.BigDecimal;

public record CurrencyConversionRequest(BigDecimal amount, String baseCurrency, String targetCurrency) {
    public static CurrencyConversionRequest of(BigDecimal amount, String baseCurrency, String targetCurrency) {
        return new CurrencyConversionRequest(amount, baseCurrency, targetCurrency);
    }
}
