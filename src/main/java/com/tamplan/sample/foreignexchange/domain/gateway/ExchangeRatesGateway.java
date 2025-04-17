package com.tamplan.sample.foreignexchange.domain.gateway;

import com.tamplan.sample.foreignexchange.domain.common.Currency;

import java.math.BigDecimal;

public interface ExchangeRatesGateway {

    BigDecimal calculateExchangeRate(Currency baseCurrency, Currency targetCurrency);
}
