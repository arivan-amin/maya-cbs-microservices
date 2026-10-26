package com.maya.cbs.ledger.domain.account.exception;

import com.maya.cbs.ledger.domain.base.Money;

public class InvalidHoldReleaseException extends RuntimeException {

    public InvalidHoldReleaseException (Money held, Money requested) {
        super("Cannot release " + requested + " when only " + held + " is held");
    }
}
