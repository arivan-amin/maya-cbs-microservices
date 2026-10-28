package com.maya.cbs.ledger.domain.coa.entity;

public enum GeneralLedgerAccountStatus {
    ACTIVE,
    INACTIVE;

    public boolean allowsPosting () {
        return this == ACTIVE;
    }
}
