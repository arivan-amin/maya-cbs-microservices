package com.maya.cbs.ledger.domain.account.entity;

import com.maya.cbs.ledger.domain.base.EntryDirection;

public enum AccountStatus {
    ACTIVE,
    FROZEN,
    CLOSED;

    public boolean allows (EntryDirection direction) {
        return switch (this) {
            case ACTIVE -> true;
            case FROZEN -> direction == EntryDirection.CREDIT;
            case CLOSED -> false;
        };
    }

    public boolean canTransitionTo (AccountStatus target) {
        return switch (this) {
            case ACTIVE -> target == FROZEN || target == CLOSED;
            case FROZEN -> target == ACTIVE || target == CLOSED;
            case CLOSED -> false;
        };
    }
}
