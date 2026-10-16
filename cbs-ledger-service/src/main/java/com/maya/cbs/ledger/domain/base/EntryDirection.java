package com.maya.cbs.ledger.domain.base;

public enum EntryDirection {
    DEBIT,
    CREDIT;

    public EntryDirection opposite () {
        return this == DEBIT ? CREDIT : DEBIT;
    }
}
