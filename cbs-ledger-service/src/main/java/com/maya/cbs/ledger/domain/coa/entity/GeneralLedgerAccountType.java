package com.maya.cbs.ledger.domain.coa.entity;

import com.maya.cbs.ledger.domain.base.EntryDirection;
import lombok.Getter;

import static com.maya.cbs.ledger.domain.base.EntryDirection.CREDIT;
import static com.maya.cbs.ledger.domain.base.EntryDirection.DEBIT;

@Getter
public enum GeneralLedgerAccountType {
    ASSET(1, DEBIT),
    LIABILITY(2, CREDIT),
    EQUITY(3, CREDIT),
    REVENUE(4, CREDIT),
    EXPENSE(5, DEBIT);

    private final int classDigit;
    private final EntryDirection normalBalanceSide;

    GeneralLedgerAccountType (int classDigit, EntryDirection normalBalanceSide) {
        this.classDigit = classDigit;
        this.normalBalanceSide = normalBalanceSide;
    }
}
