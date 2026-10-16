package com.maya.cbs.ledger.domain.account.usecase.exception;

public class InactiveAccountException extends RuntimeException {

    public InactiveAccountException (String accountNumber) {
        super("accountNumber %s is not active".formatted(accountNumber));
    }
}
