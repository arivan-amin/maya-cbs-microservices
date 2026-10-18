package com.maya.cbs.ledger.domain.coa.registry;

import com.maya.cbs.ledger.domain.coa.entity.AccountingContext;

import java.util.UUID;

public interface GeneralLedgerAccountRegistry {

    UUID resolveDebitAccount (AccountingContext context);

    UUID resolveCreditAccount (AccountingContext context);
}
