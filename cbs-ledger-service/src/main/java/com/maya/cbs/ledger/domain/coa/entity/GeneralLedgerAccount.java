package com.maya.cbs.ledger.domain.coa.entity;

import com.maya.cbs.ledger.domain.base.Currency;
import lombok.Data;

import java.util.UUID;

@Data
public final class GeneralLedgerAccount {

    private final UUID id;
    private final GeneralLedgerAccountCode code;
    private final UUID parentId;
    private final String name;
    private final GeneralLedgerAccountType type;
    private final Currency currency;
    private final boolean postingAllowed;
    private final Long version;
    private GeneralLedgerAccountStatus status;
}
