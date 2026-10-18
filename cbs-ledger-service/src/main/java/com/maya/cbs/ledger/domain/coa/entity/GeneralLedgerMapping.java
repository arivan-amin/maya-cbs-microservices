package com.maya.cbs.ledger.domain.coa.entity;

import com.maya.cbs.ledger.domain.base.Currency;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralLedgerMapping {

    private UUID id;
    private FinancialMovementType type;
    private Currency currency;
    private CustomerSegment customerSegment;
    private UUID debitGeneralLedgerAccount;
    private UUID creditGeneralLedgerAccount;
}
