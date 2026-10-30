package com.maya.cbs.ledger.domain.account.entity;

import com.maya.cbs.ledger.domain.base.Currency;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public final class Account {

    private final UUID id;
    private final String accountNumber;
    private final String name;
    private final UUID glAccountId;
    private final Currency currency;
    private final Instant openedAt;
    private final AccountBalance balance;
    private final Long version;
    private AccountStatus status;
}
