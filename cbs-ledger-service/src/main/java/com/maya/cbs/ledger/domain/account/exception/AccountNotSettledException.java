package com.maya.cbs.ledger.domain.account.exception;

import java.util.UUID;

public class AccountNotSettledException extends RuntimeException {

    public AccountNotSettledException (UUID id) {
        super("Account " + id + " has a non-zero balance or active holds");
    }
}
