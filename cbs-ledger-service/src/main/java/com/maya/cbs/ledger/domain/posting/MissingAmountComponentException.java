package com.maya.cbs.ledger.domain.posting;

public class MissingAmountComponentException extends RuntimeException {

    public MissingAmountComponentException (AmountComponent component) {
        super("Missing amount component for: %s".formatted(component));
    }
}
