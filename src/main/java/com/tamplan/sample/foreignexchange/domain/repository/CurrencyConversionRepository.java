package com.tamplan.sample.foreignexchange.domain.repository;

import com.tamplan.sample.foreignexchange.domain.entity.CurrencyConversionResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CurrencyConversionRepository {

    // update
    void saveCurrencyConversionResult(CurrencyConversionResult currencyConversionResult);

    // read
    Optional<CurrencyConversionResult> findByTransactionId(String transactionId);
    List<CurrencyConversionResult> getCurrencyConversionResults(LocalDate transactionDate, String transactionId, int page, int size);

}
