package com.tamplan.sample.foreignexchange.infra.outbound.repository;

import com.tamplan.sample.foreignexchange.domain.entity.CurrencyConversionResult;
import com.tamplan.sample.foreignexchange.domain.repository.CurrencyConversionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<CurrencyConversionResult> findByTransactionId(String transactionId) {
        return springDataCurrencyConversionRepository.findById(transactionId);

    }

    @Override
    public List<CurrencyConversionResult> getCurrencyConversionResults(
            LocalDate transactionDate, String transactionId, int page, int size) {
        return springDataCurrencyConversionRepository.findByTransactionDateAndTransactionId(
                transactionDate, transactionId, PageRequest.of(page-1, size)).toList();
    }
}
