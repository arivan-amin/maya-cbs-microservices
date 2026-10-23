package com.maya.cbs.ledger.domain.posting;

public class MissingAccountBindingException extends RuntimeException {

    public MissingAccountBindingException (AccountRole role) {
        super("Missing account binding for: " + role);
    }
}
