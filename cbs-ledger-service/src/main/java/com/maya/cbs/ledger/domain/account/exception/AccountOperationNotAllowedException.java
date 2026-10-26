package com.maya.cbs.ledger.domain.account.exception;

import com.maya.cbs.ledger.domain.account.entity.AccountStatus;
import com.maya.cbs.ledger.domain.base.EntryDirection;

import java.util.UUID;

public class AccountOperationNotAllowedException extends RuntimeException {

    public AccountOperationNotAllowedException (UUID id, AccountStatus status,
                                                EntryDirection entryDirection) {
        super("Operation %s on account: %s with status:%s".formatted(entryDirection, id, status));
    }
}
