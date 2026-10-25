package com.maya.cbs.ledger.domain.account.exception;

import com.maya.cbs.ledger.domain.account.entity.AccountRole;

public class MissingAccountBindingException extends RuntimeException {

    public MissingAccountBindingException (AccountRole role) {
        super("Missing account binding for: " + role);
    }
}
