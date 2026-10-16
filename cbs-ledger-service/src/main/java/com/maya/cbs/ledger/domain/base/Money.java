package com.maya.cbs.ledger.domain.base;

import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Value
public class Money {

    public static final RoundingMode CONVERSION_ROUNDING = RoundingMode.HALF_EVEN;

    BigDecimal amount;
    Currency currency;

    private Money (BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money zero (Currency currency) {
        return of(BigDecimal.ZERO, currency);
    }

    public static Money of (BigDecimal amount, Currency currency) {
        Objects.requireNonNull(amount, "amount");
        Objects.requireNonNull(currency, "currency");
        int maxDigits = currency.getMaxAllowedFractionDigits();
        if (amount.stripTrailingZeros()
                .scale() > maxDigits) {
            throw new InvalidMoneyException(amount, currency);
        }
        return new Money(amount.setScale(maxDigits, RoundingMode.UNNECESSARY), currency);
    }

    public boolean isZero () {
        return amount.signum() == 0;
    }

    public boolean isNegative () {
        return amount.signum() < 0;
    }

    public Money add (Money other) {
        requireSameCurrency(other);
        return of(amount.add(other.amount), currency);
    }

    private void requireSameCurrency (Money other) {
        Objects.requireNonNull(other, "other");
        if (currency != other.currency) {
            throw new CurrencyMismatchException(
                "Cannot combine %s with %s".formatted(currency, other.currency));
        }
    }

    public boolean isPositive () {
        return amount.signum() > 0;
    }

    public Money subtract (Money other) {
        requireSameCurrency(other);
        return of(amount.subtract(other.amount), currency);
    }
}
