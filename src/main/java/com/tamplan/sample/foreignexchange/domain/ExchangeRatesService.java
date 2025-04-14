package com.tamplan.sample.foreignexchange.domain;

import com.tamplan.sample.foreignexchange.domain.entity.CurrencyConversionResult;
import com.tamplan.sample.foreignexchange.domain.exception.InvalidAmountException;
import com.tamplan.sample.foreignexchange.domain.exception.InvalidCurrencyException;
import com.tamplan.sample.foreignexchange.domain.repository.CurrencyConversionRepository;
import com.tamplan.sample.foreignexchange.util.RandomIdGenerator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExchangeRatesService {

    private final ExchangeRatesGateway exchangeRatesGateway;
    private final CurrencyConversionRepository currencyConversionRepository;

    public ExchangeRatesService(ExchangeRatesGateway exchangeRatesGateway, CurrencyConversionRepository currencyConversionRepository) {
        this.exchangeRatesGateway = exchangeRatesGateway;
        this.currencyConversionRepository = currencyConversionRepository;
    }

    /**
     * Calculates the exchange rate between two currencies.
     *
     * @param baseCurrencyCode  the base currency code
     * @param targetCurrencyCode the target currency code
     * @return the exchange rate
     */
    public BigDecimal calculateExchangeRate(String baseCurrencyCode, String targetCurrencyCode) {
        var currencies = validateCurrencyCodes(baseCurrencyCode, targetCurrencyCode);

        var baseCurrency = currencies.get(0);
        var targetCurrency = currencies.get(1);

        return exchangeRatesGateway.calculateExchangeRate(baseCurrency, targetCurrency);
    }

    private List<Currency> validateCurrencyCodes(String baseCurrencyCode, String targetCurrencyCode) {
        var baseCurrency = validateCurrencyCode(baseCurrencyCode);
        var targetCurrency = validateCurrencyCode(targetCurrencyCode);

        if (baseCurrency == targetCurrency) {
            throw new InvalidCurrencyException(
                    "Base and target currencies cannot be the same, base: [%s], target: [%s]".formatted(baseCurrencyCode, targetCurrencyCode));
        }

        return List.of(baseCurrency, targetCurrency);
    }

    private Currency validateCurrencyCode(String currencyCode) {
        if (currencyCode == null || currencyCode.isBlank()) {
            throw new InvalidCurrencyException("currency code cannot be null or blank");
        }
        return Currency.findByCode(currencyCode)
                .orElseThrow(() -> new InvalidCurrencyException("invalid currency: [%s]".formatted(currencyCode)));
    }

    /**
     * Converts the given amount from base currency to target currency.
     *
     * @param amount            the amount to convert
     * @param baseCurrencyCode  the base currency code
     * @param targetCurrencyCode the target currency code
     * @return the conversion result
     */
    public CurrencyConversionResult convertAmount(BigDecimal amount, String baseCurrencyCode, String targetCurrencyCode) {
        validateAmount(amount);
        var currencies =  validateCurrencyCodes(baseCurrencyCode, targetCurrencyCode);

        var baseCurrency = currencies.get(0);
        var targetCurrency = currencies.get(1);

        var txnId = RandomIdGenerator.generateRandomId();

        var conversionResult = new CurrencyConversionResult();
        conversionResult.setTransactionId(txnId);
        conversionResult.setBaseCurrency(baseCurrency.name());
        conversionResult.setTargetCurrency(targetCurrency.name());
        conversionResult.setAmount(amount);
        conversionResult.setConvertedAmount(exchangeRatesGateway.convertAmount(amount, baseCurrency, targetCurrency));

        currencyConversionRepository.saveCurrencyConversionResult(conversionResult);

        return conversionResult;
    }


    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("amount cannot be null or less than or equal to zero");
        }
    }
}
