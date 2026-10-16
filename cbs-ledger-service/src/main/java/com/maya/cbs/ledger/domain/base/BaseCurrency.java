package com.maya.cbs.ledger.domain.base;

import lombok.Value;

import java.util.Objects;

@Value
public class BaseCurrency {

    Currency currency;

    public BaseCurrency (Currency currency) {
        Objects.requireNonNull(currency, "currency must not be null");
        if (currency.getMaxAllowedFractionDigits() < 0) {
            throw new IllegalArgumentException(
                "Not a circulating ISO 4217 currency: " + currency.getCurrencyCode());
        }
        this.currency = currency;
    }

    public String currencyCode () {
        return currency.getCurrencyCode();
    }

    public boolean isSameAs (Currency other) {
        return currency == other;
    }
}
