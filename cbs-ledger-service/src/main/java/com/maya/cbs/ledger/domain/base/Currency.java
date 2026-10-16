package com.maya.cbs.ledger.domain.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Currency {
    IQD("IQD", 3),
    USD("USD", 2),
    EUR("EUR", 2),
    GBP("GBP", 2),
    AED("AED", 2);

    private static final Map<String, Currency> BY_CODE = Arrays.stream(values())
        .collect(Collectors.toUnmodifiableMap(Currency::getCurrencyCode, Function.identity()));

    private final String currencyCode;
    private final int maxAllowedFractionDigits;

    public static Currency fromCode (String code) {
        Currency currency = BY_CODE.get(code);
        if (currency == null) {
            throw new UnknownCurrencyException(code);
        }
        return currency;
    }
}
