package com.maya.cbs.ledger.domain.account.entity;

import com.maya.cbs.ledger.domain.account.exception.InsufficientAccountBalanceException;
import com.maya.cbs.ledger.domain.base.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
public class AccountBalance {

    private final String accountId;
    private Money accountBalance;
    private Money availableBalance;
    private Money overdraftLimit;
    
    public AccountBalance (String accountId, Currency currency) {
        this.accountId = Objects.requireNonNull(accountId, "Account ID cannot be null");
        accountBalance = Money.zero(currency);
        availableBalance = Money.zero(currency);
    }

    public void applyPostedMovement (Money delta, EntryDirection direction) {
        if (direction == EntryDirection.CREDIT) {
            accountBalance = accountBalance.add(delta);
            availableBalance = availableBalance.add(delta);
        }
        else {
            validateSufficientLedger(delta);
            accountBalance = accountBalance.subtract(delta);
            availableBalance = availableBalance.subtract(delta);
        }
    }

    public void validateSufficientLedger (Money delta) {
        if (!hasSufficientBalance(delta)) {
            throw new InsufficientAccountBalanceException(accountBalance, delta);
        }
    }

    public boolean hasSufficientBalance (Money amountToCheck) {
        Objects.requireNonNull(amountToCheck, "Amount to check cannot be null");
        Money availableFunds = availableBalance.add(overdraftLimit);
        return availableFunds.isGreaterThanOrEqual(amountToCheck);
    }

    public void placeHold (Money holdAmount) {
        if (availableBalance.isLessThan(holdAmount)) {
            throw new InsufficientAccountBalanceException(availableBalance, holdAmount);
        }
        availableBalance = availableBalance.subtract(holdAmount);
    }

    public void releaseHold (Money holdAmount) {
        availableBalance = availableBalance.add(holdAmount);
    }
}
