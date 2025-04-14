package com.tamplan.sample.foreignexchange.infra.outbound.repository;

import com.tamplan.sample.foreignexchange.domain.entity.CurrencyConversionResult;
import com.tamplan.sample.foreignexchange.domain.repository.CurrencyConversionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JpaCurrencyConversionRepository implements CurrencyConversionRepository {

    private final SpringDataCurrencyConversionRepository springDataCurrencyConversionRepository;

    public JpaCurrencyConversionRepository(SpringDataCurrencyConversionRepository springDataCurrencyConversionRepository) {
        this.springDataCurrencyConversionRepository = springDataCurrencyConversionRepository;
    }

    @Transactional
    @Override
    public void saveCurrencyConversionResult(CurrencyConversionResult currencyConversionResult) {
        springDataCurrencyConversionRepository.save(currencyConversionResult);
    }
}
