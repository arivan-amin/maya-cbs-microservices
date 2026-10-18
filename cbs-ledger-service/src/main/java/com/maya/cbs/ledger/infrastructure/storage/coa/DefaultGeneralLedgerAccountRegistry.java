package com.maya.cbs.ledger.infrastructure.storage.coa;

import com.maya.cbs.ledger.domain.coa.entity.AccountingContext;
import com.maya.cbs.ledger.domain.coa.entity.GeneralLedgerMapping;
import com.maya.cbs.ledger.domain.coa.exceptions.AccountMappingNotFoundException;
import com.maya.cbs.ledger.domain.coa.registry.GeneralLedgerAccountRegistry;

import java.util.UUID;

public class DefaultGeneralLedgerAccountRegistry implements GeneralLedgerAccountRegistry {

    @Override
    public UUID resolveDebitAccount (AccountingContext context) {
        GeneralLedgerMapping mapping = new GeneralLedgerMapping();
        return mapping.getDebitGeneralLedgerAccount();
    }

    @Override
    public UUID resolveCreditAccount (AccountingContext context) {
        GeneralLedgerMapping mapping = new GeneralLedgerMapping();
        new AccountMappingNotFoundException(context);

        return mapping.getCreditGeneralLedgerAccount();
    }
}
