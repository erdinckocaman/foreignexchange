package com.tamplan.sample.foreignexchange;


import com.tamplan.sample.foreignexchange.domain.entity.CurrencyConversionResult;
import com.tamplan.sample.foreignexchange.domain.exception.InvalidAmountException;
import com.tamplan.sample.foreignexchange.domain.exception.InvalidCurrencyException;
import com.tamplan.sample.foreignexchange.domain.gateway.ExchangeRatesGateway;
import com.tamplan.sample.foreignexchange.domain.repository.CurrencyConversionRepository;
import com.tamplan.sample.foreignexchange.domain.service.ExchangeRatesService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ExchangeRatesServiceTest {

    @Test
    void shouldReturnExchangeRate() {
        // Given
        var exchangeRatesGateway =  mock(ExchangeRatesGateway.class);
        var currencyConversionRepository =  mock(CurrencyConversionRepository.class);

        when(exchangeRatesGateway.calculateExchangeRate(any(), any())).thenReturn(new BigDecimal("0.85"));

        ExchangeRatesService exchangeRatesService = new ExchangeRatesService(
                exchangeRatesGateway, currencyConversionRepository);
        var baseCurrency = "USD";
        var targetCurrency = "EUR";

        // When
        var exchangeRate = exchangeRatesService.calculateExchangeRate(baseCurrency, targetCurrency);

        // Then
        assertNotNull(exchangeRate);
        assertEquals(new BigDecimal("0.85"), exchangeRate);
    }

    @Test
    void shouldGiveErrorWhenUndefinedCurrencySupplied() {
        // Given
        var exchangeRatesGateway =  mock(ExchangeRatesGateway.class);
        var currencyConversionRepository =  mock(CurrencyConversionRepository.class);

        var exchangeRatesService = new ExchangeRatesService(
                exchangeRatesGateway, currencyConversionRepository);

        var baseCurrency = "USD";
        var targetCurrency = "EUR_INVALID";

        // When
        // This should throw an exception
        assertThrows(InvalidCurrencyException.class, () -> {
            exchangeRatesService.calculateExchangeRate(baseCurrency, targetCurrency);
        });
    }

    @Test
    void shouldCalculateAmountConversion() {
        // Given
        var exchangeRatesGateway =  mock(ExchangeRatesGateway.class);
        var currencyConversionRepository =  mock(CurrencyConversionRepository.class);
        var amount = new BigDecimal("100");
        var exchangeRate = new BigDecimal("0.85");
        when(exchangeRatesGateway.calculateExchangeRate(any(), any())).thenReturn(exchangeRate);

        var currencyConversionResult = new CurrencyConversionResult();
        currencyConversionResult.setConvertedAmount(amount.multiply(exchangeRate));
        when(currencyConversionRepository.findByTransactionId(any())).thenReturn(Optional.of(currencyConversionResult));

        var exchangeRatesService = new ExchangeRatesService(exchangeRatesGateway, currencyConversionRepository);
        var baseCurrency = "USD";
        var targetCurrency = "EUR";

        // When
        var result = exchangeRatesService.convertAmount(amount, baseCurrency, targetCurrency);

        // Then
        assertNotNull(result);
        assertEquals(new BigDecimal("85.00"), result.getConvertedAmount());
    }

    @Test
    void shouldGiveErrorWhenAmountIsNegative() {
        // Given
        var exchangeRatesGateway =  mock(ExchangeRatesGateway.class);
        var currencyConversionRepository =  mock(CurrencyConversionRepository.class);
        var amount = new BigDecimal("-100");
        var exchangeRate = new BigDecimal("0.85");
        when(exchangeRatesGateway.calculateExchangeRate(any(), any())).thenReturn(exchangeRate);

        var exchangeRatesService = new ExchangeRatesService(exchangeRatesGateway, currencyConversionRepository);
        var baseCurrency = "USD";
        var targetCurrency = "EUR";

        // When
        // This should throw an exception
        assertThrows(InvalidAmountException.class,
                () -> exchangeRatesService.convertAmount(amount, baseCurrency, targetCurrency));
    }
}
