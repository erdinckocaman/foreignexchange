package com.tamplan.sample.foreignexchange.infra.inbound.rest.model;

import com.tamplan.sample.foreignexchange.domain.entity.CurrencyConversionResult;

import java.math.BigDecimal;

public record CurrencyConversionResponse(BigDecimal convertedAmount, String transactionId) {
    public static CurrencyConversionResponse of(CurrencyConversionResult currencyConversionResult) {
        return new CurrencyConversionResponse(
                currencyConversionResult.getConvertedAmount(),
                currencyConversionResult.getTransactionId()
        );
    }
}
