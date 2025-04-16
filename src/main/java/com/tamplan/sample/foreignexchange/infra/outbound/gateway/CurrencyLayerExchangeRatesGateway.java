package com.tamplan.sample.foreignexchange.infra.outbound.gateway;

import com.tamplan.sample.foreignexchange.domain.common.Currency;
import com.tamplan.sample.foreignexchange.domain.gateway.ExchangeRatesGateway;
import com.tamplan.sample.foreignexchange.infra.outbound.gateway.exception.CurrencyLayerFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class CurrencyLayerExchangeRatesGateway implements ExchangeRatesGateway {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyLayerExchangeRatesGateway.class);

    @Value("${app.currency-service.url}")
    private String baseUrl;

    @Value("${app.currency-service.access-key}")
    private String accessKey;

    private final RestTemplate restTemplate;

    private long lastRequestTime = 0;

    public CurrencyLayerExchangeRatesGateway(@Value("${app.currency-service.connect-timeout}") Long connectTimeout,
                                             @Value("${app.currency-service.read-timeout}") Long readTimeout) {
        this.restTemplate =  new RestTemplateBuilder().
                connectTimeout(Duration.ofMillis(connectTimeout)).
                readTimeout(Duration.ofMillis(readTimeout)).build();
    }

    @Override
    public BigDecimal calculateExchangeRate(Currency baseCurrency, Currency targetCurrency) {
        return getRate(baseCurrency, targetCurrency);
    }

    @Override
    public BigDecimal convertAmount(BigDecimal amount, Currency baseCurrency, Currency targetCurrency) {
        return getRate(baseCurrency, targetCurrency).multiply(amount);
    }

    private BigDecimal getRate(Currency baseCurrency, Currency targetCurrency) {
        waitToAvoidRateLimit();

        var url =  "%s?access_key=%s&currencies=%s&source=%s&format=1"
                .formatted(baseUrl, accessKey, targetCurrency.name(), baseCurrency.name());

        logger.info("Fetching exchange rate from URL: [{}]", url);

        HashMap<String, Object> response = restTemplate.getForObject(url, HashMap.class);
        lastRequestTime = System.currentTimeMillis();

        if (response == null || !((boolean) response.get("success"))) {
            logger.error("Response from CurrencyLayer is null or unsuccessful, response: [{}]", response);
            throw new CurrencyLayerFailedException();
        }

        var quotes = (Map<String, Number>)response.get("quotes");
        var valueKey = baseCurrency.name() + targetCurrency.name();

        if (!quotes.containsKey(valueKey)) {
            throw new CurrencyLayerFailedException("valueKey not found: " + valueKey);
        }
        return BigDecimal.valueOf(quotes.get(valueKey).doubleValue());
    }

    /**
     * Waits to avoid hitting the rate limit of the CurrencyLayer API.
     * The API allows 1 request per second, so we wait if the last request was made less than 1 second ago.
     */
    private void waitToAvoidRateLimit() {
        long currentTime = System.currentTimeMillis();
        long timeDiff = currentTime - lastRequestTime;
        if (timeDiff < 1000) {
            try {
                Thread.sleep(1000 - timeDiff);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
