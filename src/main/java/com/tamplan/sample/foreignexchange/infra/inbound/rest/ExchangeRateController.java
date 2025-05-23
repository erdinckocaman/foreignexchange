package com.tamplan.sample.foreignexchange.infra.inbound.rest;

import com.tamplan.sample.foreignexchange.domain.service.ExchangeRatesService;
import com.tamplan.sample.foreignexchange.infra.inbound.rest.model.CurrencyConversionRequest;
import com.tamplan.sample.foreignexchange.infra.inbound.rest.model.CurrencyConversionResponse;
import com.tamplan.sample.foreignexchange.infra.inbound.rest.model.ExchangeRateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping(value = "/exchange-rates")
public class ExchangeRateController {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateController.class);

    private final ExchangeRatesService exchangeRateService;

    public ExchangeRateController(ExchangeRatesService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping(produces = "application/json")
    public ExchangeRateResponse calculateExchangeRate(@RequestParam String base, @RequestParam String target) {
        logger.info("Base currency: [{}], target currency: [{}]", base, target);
        return ExchangeRateResponse.of(exchangeRateService.calculateExchangeRate(base, target));
    }

    @PostMapping("/currency-conversion")
    public CurrencyConversionResponse convertAmount(@RequestBody CurrencyConversionRequest request) {
        logger.info("Currency conversion request: [{}]", request);
        return CurrencyConversionResponse.of(
                exchangeRateService.convertAmount(request.amount(), request.baseCurrency(), request.targetCurrency()));
    }

    @PostMapping(value = "/bulk-currency-conversion", consumes = "text/csv", produces = "application/json")
    public List<CurrencyConversionResponse> bulkConvertAmount(InputStream inputStream) {
        logger.info("Streaming bulk currency conversion requested");
        return exchangeRateService.convertAmounts(inputStream);
    }

    @GetMapping("/currency-conversion-history")
    public List<CurrencyConversionResponse> getCurrencyConversions(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate transactionDate,
            @RequestParam(required = false) String transactionId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        logger.info("Get currency conversions, transaction date: [{}], transactionId: [{}]", transactionDate, transactionId);
        return exchangeRateService.getCurrencyConversions(transactionDate, transactionId, page, size)
                .stream()
                .map(CurrencyConversionResponse::of)
                .toList();
    }
}
