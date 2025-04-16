package com.tamplan.sample.foreignexchange.domain.service;

import com.tamplan.sample.foreignexchange.domain.common.Currency;
import com.tamplan.sample.foreignexchange.domain.entity.CurrencyConversionResult;
import com.tamplan.sample.foreignexchange.domain.exception.InvalidAmountException;
import com.tamplan.sample.foreignexchange.domain.exception.InvalidCurrencyException;
import com.tamplan.sample.foreignexchange.domain.exception.InvalidUserInputException;
import com.tamplan.sample.foreignexchange.domain.gateway.ExchangeRatesGateway;
import com.tamplan.sample.foreignexchange.domain.repository.CurrencyConversionRepository;
import com.tamplan.sample.foreignexchange.infra.inbound.rest.model.CurrencyConversionResponse;
import com.tamplan.sample.foreignexchange.domain.exception.UndefinedException;
import com.tamplan.sample.foreignexchange.util.RandomIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Service
public class ExchangeRatesService {

    private static final Logger logger  = LoggerFactory.getLogger(ExchangeRatesService.class);

    @Value("${app.bulk-currency-conversion.max}")
    private int maxBulkCurrencyConversions;
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
     * Converts a list of amounts from base currency to target currency.
     *
     * @param inputStream the input stream containing the amounts to convert
     * @return a list of conversion results
     */

    public List<CurrencyConversionResponse> convertAmounts(InputStream inputStream) {
        List<CurrencyConversionResponse> responses = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (maxBulkCurrencyConversions <= responses.size()) {
                    throw new InvalidUserInputException(
                            "Maximum bulk currency conversions exceeded, max: [%d]".formatted(maxBulkCurrencyConversions));
                }

                String[] values = line.split(",");
                if (values.length<3) {
                    logger.info("Invalid line: [{}], expected 3 values", line);
                    continue;
                }

                BigDecimal amount = new BigDecimal(values[0].trim());
                String baseCurrency = values[1].trim();
                String targetCurrency = values[2].trim();

                var conversionResult = convertAmount(amount, baseCurrency, targetCurrency);

                responses.add(CurrencyConversionResponse.of(conversionResult));
            }
        } catch (IOException e) {
            throw new UndefinedException(e);
        }

        return responses;
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

        return currencyConversionRepository.findByTransactionId(txnId).orElseThrow();
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("amount cannot be null or less than or equal to zero");
        }
    }

    public List<CurrencyConversionResult> getCurrencyConversions(LocalDate transactionDate, String transactionId, int page, int size) {
        if (transactionDate == null && transactionId == null) {
            throw new InvalidUserInputException("Either transaction date or transaction ID must be provided");
        }

        if (page <= 0 || size <= 0) {
            throw new InvalidUserInputException("Page number and size must be greater than zero");
        }

        return currencyConversionRepository.getCurrencyConversionResults(transactionDate, transactionId, page, size);
    }

}
