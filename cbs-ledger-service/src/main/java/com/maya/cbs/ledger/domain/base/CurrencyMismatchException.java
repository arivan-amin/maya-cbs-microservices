package com.maya.cbs.ledger.domain.base;

public class CurrencyMismatchException extends RuntimeException {

    public CurrencyMismatchException (String message) {
        super(message);
    }
}
