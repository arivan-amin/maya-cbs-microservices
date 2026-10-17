package com.maya.cbs.ledger.domain.account.exception;

import com.maya.cbs.ledger.domain.base.Money;

public class InsufficientAccountBalanceException extends RuntimeException {

    public InsufficientAccountBalanceException (Money balance, Money delta) {
        super("Insufficient account balance, current balance=%s %s, delta amount=%s %s".formatted(
            balance.getAmount(), balance.getCurrency()
                .getCurrencyCode(), delta.getAmount(), delta.getCurrency()
                .getCurrencyCode()));
    }
}
