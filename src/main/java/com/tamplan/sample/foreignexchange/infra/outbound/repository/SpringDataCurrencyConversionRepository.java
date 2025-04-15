package com.tamplan.sample.foreignexchange.infra.outbound.repository;

import com.tamplan.sample.foreignexchange.domain.entity.CurrencyConversionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SpringDataCurrencyConversionRepository extends JpaRepository<CurrencyConversionResult, String> {

    @Query("SELECT ccr FROM CurrencyConversionResult ccr WHERE " +
            "(:transactionId IS NULL OR ccr.transactionId = :transactionId) AND " +
            "(:transactionDate IS NULL OR CAST(ccr.createdAt AS java.time.LocalDate) = :transactionDate)")
    Page<CurrencyConversionResult> findByTransactionDateAndTransactionId(
            @Param("transactionDate") LocalDate transactionDate,
            @Param("transactionId") String transactionId,
            Pageable pageable);
}
