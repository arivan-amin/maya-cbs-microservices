package com.maya.cbs.ledger.domain.account.entity;

import com.maya.cbs.ledger.domain.base.EntryDirection;
import lombok.Getter;

@Getter
public enum AccountType {
    ASSET(EntryDirection.DEBIT),
    EXPENSE(EntryDirection.DEBIT),
    LIABILITY(EntryDirection.CREDIT),
    EQUITY(EntryDirection.CREDIT),
    INCOME(EntryDirection.CREDIT);

    private final EntryDirection normalBalanceSide;

    AccountType (EntryDirection normalBalanceSide) {
        this.normalBalanceSide = normalBalanceSide;
    }
}
