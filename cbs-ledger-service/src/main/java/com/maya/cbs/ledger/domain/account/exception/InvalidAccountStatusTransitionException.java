package com.maya.cbs.ledger.domain.account.exception;

import com.maya.cbs.ledger.domain.account.entity.AccountStatus;

public class InvalidAccountStatusTransitionException extends RuntimeException {

    public InvalidAccountStatusTransitionException (AccountStatus from, AccountStatus to) {
        super("Cannot transition account from " + from + " to " + to);
    }
}
