package com.tamplan.sample.foreignexchange.infra.inbound.rest.model;

import com.tamplan.sample.foreignexchange.domain.entity.CurrencyConversionResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CurrencyConversionResponse(BigDecimal convertedAmount, String transactionId, LocalDateTime createdAt) {
    public static CurrencyConversionResponse of(CurrencyConversionResult currencyConversionResult) {
        return new CurrencyConversionResponse(
                currencyConversionResult.getConvertedAmount(),
                currencyConversionResult.getTransactionId(),
                currencyConversionResult.getCreatedAt()
        );
    }
}
