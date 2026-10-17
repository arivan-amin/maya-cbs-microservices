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
    private Money ledgerBalance;
    private Money availableBalance;

    public AccountBalance (String accountId, Currency currency) {
        this.accountId = Objects.requireNonNull(accountId, "Account ID cannot be null");
        ledgerBalance = Money.zero(currency);
        availableBalance = Money.zero(currency);
    }

    public void applyPostedMovement (Money delta, EntryDirection direction) {
        if (direction == EntryDirection.CREDIT) {
            ledgerBalance = ledgerBalance.add(delta);
            availableBalance = availableBalance.add(delta);
        }
        else {
            validateSufficientLedger(delta);
            ledgerBalance = ledgerBalance.subtract(delta);
            availableBalance = availableBalance.subtract(delta);
        }
    }

    private void validateSufficientLedger (Money delta) {
        if (ledgerBalance.isLessThan(delta)) {
            throw new InsufficientAccountBalanceException(ledgerBalance, delta);
        }
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
