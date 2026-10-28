package com.maya.cbs.ledger.domain.account.entity;

import com.maya.cbs.ledger.domain.base.EntryDirection;
import lombok.Getter;

import static com.maya.cbs.ledger.domain.base.EntryDirection.CREDIT;
import static com.maya.cbs.ledger.domain.base.EntryDirection.DEBIT;

@Getter
public enum AccountType {
    ASSET(DEBIT),
    EXPENSE(DEBIT),
    LIABILITY(CREDIT),
    EQUITY(CREDIT),
    REVENUE(CREDIT);

    private final EntryDirection normalBalanceSide;

    AccountType (EntryDirection normalBalanceSide) {
        this.normalBalanceSide = normalBalanceSide;
    }
}
