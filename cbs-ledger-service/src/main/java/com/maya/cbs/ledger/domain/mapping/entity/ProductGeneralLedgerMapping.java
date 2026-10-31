package com.maya.cbs.ledger.domain.mapping.entity;

import com.maya.cbs.ledger.domain.base.Currency;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductGeneralLedgerMapping {

    private final UUID id;
    private final UUID productId;
    private final Currency currency;
    private final AccountingPurpose accountingPurpose;
    private final UUID generalLedgerAccountId;
    private final ProductGeneralLedgerMappingStatus status;
}
