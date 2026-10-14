package com.maya.cbs.ledger.domain.account.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class Account {

    private final UUID id;
    private final String accountNumber;
    private final String name;
    private final AccountType type;
    private final AccountStatus status;
}
