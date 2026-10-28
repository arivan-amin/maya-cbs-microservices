package com.maya.cbs.ledger.domain.account.entity;

import com.maya.cbs.ledger.domain.account.exception.InsufficientAccountBalanceException;
import com.maya.cbs.ledger.domain.account.exception.InvalidHoldReleaseException;
import com.maya.cbs.ledger.domain.base.Currency;
import com.maya.cbs.ledger.domain.base.Money;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
public final class AccountBalance {

    private final UUID accountId;
    private final Currency currency;
    private Money ledgerBalance;
    private Money heldAmount;
    private Money overdraftLimit;

    private AccountBalance (UUID accountId, Currency currency, Money ledgerBalance,
                            Money heldAmount, Money overdraftLimit) {
        this.accountId = Objects.requireNonNull(accountId, "Account ID cannot be null");
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");
        this.ledgerBalance = requireSameCurrency(ledgerBalance);
        this.heldAmount = requireNonNegative(requireSameCurrency(heldAmount), "Held amount");
        this.overdraftLimit =
            requireNonNegative(requireSameCurrency(overdraftLimit), "Overdraft limit");
    }

    private Money requireSameCurrency (Money money) {
        Objects.requireNonNull(money, "Money cannot be null");
        if (currency != money.getCurrency()) {
            throw new IllegalArgumentException(
                "Currency mismatch: expected " + currency + " but was " + money.getCurrency());
        }
        return money;
    }

    private Money requireNonNegative (Money money, String label) {
        if (money.isLessThan(Money.zero(currency))) {
            throw new IllegalArgumentException(label + " cannot be negative");
        }
        return money;
    }

    public static AccountBalance create (UUID accountId, Currency currency) {
        Objects.requireNonNull(currency, "Currency cannot be null");
        return new AccountBalance(accountId, currency, Money.zero(currency), Money.zero(currency),
            Money.zero(currency));
    }

    public static AccountBalance hydrate (UUID accountId, Currency currency, Money ledgerBalance,
                                          Money heldAmount, Money overdraftLimit) {
        return new AccountBalance(accountId, currency, ledgerBalance, heldAmount, overdraftLimit);
    }

    public Money availableBalance () {
        return ledgerBalance.subtract(heldAmount);
    }

    public Money spendingPower () {
        return availableBalance().add(overdraftLimit);
    }

    void captureHold (Money holdAmount, Money captureAmount) {
        requirePositiveSameCurrency(holdAmount);
        requirePositiveSameCurrency(captureAmount);
        if (heldAmount.isLessThan(holdAmount)) {
            throw new InvalidHoldReleaseException(heldAmount, holdAmount);
        }
        if (holdAmount.isLessThan(captureAmount)) {
            throw new IllegalArgumentException("Capture amount cannot exceed the hold amount");
        }
        heldAmount = heldAmount.subtract(holdAmount);
        ledgerBalance = ledgerBalance.subtract(captureAmount);
    }

    private void requirePositiveSameCurrency (Money amount) {
        requireSameCurrency(amount);
        if (!amount.isPositive()) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    public boolean canSpend (Money amount) {
        requirePositiveSameCurrency(amount);
        return spendingPower().isGreaterThanOrEqual(amount);
    }

    public boolean isFullySettled () {
        return ledgerBalance.isZero() && heldAmount.isZero();
    }

    void increase (Money amount) {
        requirePositiveSameCurrency(amount);
        ledgerBalance = ledgerBalance.add(amount);
    }

    void decrease (Money amount) {
        requireCanSpend(amount);
        ledgerBalance = ledgerBalance.subtract(amount);
    }

    void placeHold (Money amount) {
        requireCanSpend(amount);
        heldAmount = heldAmount.add(amount);
    }

    void releaseHold (Money amount) {
        requirePositiveSameCurrency(amount);
        if (heldAmount.isLessThan(amount)) {
            throw new IllegalStateException("Invalid hold on account balance");
        }
        heldAmount = heldAmount.subtract(amount);
    }

    void changeOverdraftLimit (Money newLimit) {
        requireNonNegative(requireSameCurrency(newLimit), "Overdraft limit");
        overdraftLimit = newLimit;
    }

    private void requireCanSpend (Money amount) {
        if (!canSpend(amount)) {
            throw new InsufficientAccountBalanceException(spendingPower(), amount);
        }
    }
}
