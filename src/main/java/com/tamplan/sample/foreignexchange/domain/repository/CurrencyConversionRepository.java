package com.tamplan.sample.foreignexchange.domain.repository;

import com.tamplan.sample.foreignexchange.domain.entity.CurrencyConversionResult;

public interface CurrencyConversionRepository {

    void saveCurrencyConversionResult(CurrencyConversionResult currencyConversionResult);
}
