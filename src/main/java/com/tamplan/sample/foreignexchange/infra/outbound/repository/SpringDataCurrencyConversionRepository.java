package com.tamplan.sample.foreignexchange.infra.outbound.repository;

import com.tamplan.sample.foreignexchange.domain.entity.CurrencyConversionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCurrencyConversionRepository extends JpaRepository<CurrencyConversionResult, String> {
}
