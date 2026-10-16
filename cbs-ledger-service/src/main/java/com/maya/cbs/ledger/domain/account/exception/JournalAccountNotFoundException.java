package com.maya.cbs.ledger.domain.account.exception;

public class JournalAccountNotFoundException extends RuntimeException {

    public JournalAccountNotFoundException (String accountNumber) {
        super("Account with accountNumber %s not found".formatted(accountNumber));
    }
}
