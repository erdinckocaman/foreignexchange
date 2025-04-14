package com.tamplan.sample.foreignexchange.infra.outbound.gateway;

import com.tamplan.sample.foreignexchange.domain.Currency;
import com.tamplan.sample.foreignexchange.domain.ExchangeRatesGateway;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CurrencyLayerExchangeRatesGateway implements ExchangeRatesGateway {

    @Override
    public BigDecimal calculateExchangeRate(Currency baseCurrency, Currency targetCurrency) {
        return BigDecimal.ONE;
    }

    @Override
    public BigDecimal convertAmount(BigDecimal amount, Currency baseCurrency, Currency targetCurrency) {
        return  BigDecimal.TEN;
    }
}
