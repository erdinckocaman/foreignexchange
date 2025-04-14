package com.tamplan.sample.foreignexchange.domain;

import java.util.Optional;

public enum Currency {
    USD,
    EUR,
    TRY;

    public static Optional<Currency> findByCode(String code) {
        for (Currency currencyCode : values()) {
            if (currencyCode.name().equalsIgnoreCase(code)) {
                return Optional.of(currencyCode);
            }
        }
        return Optional.empty();
    }
}
