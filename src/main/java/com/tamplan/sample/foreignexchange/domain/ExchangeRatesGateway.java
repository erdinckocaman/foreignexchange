package com.tamplan.sample.foreignexchange.domain;

import java.math.BigDecimal;

public interface ExchangeRatesGateway {

    BigDecimal calculateExchangeRate(Currency baseCurrency, Currency targetCurrency);
    BigDecimal convertAmount(BigDecimal amount, Currency baseCurrency, Currency targetCurrency);

}
