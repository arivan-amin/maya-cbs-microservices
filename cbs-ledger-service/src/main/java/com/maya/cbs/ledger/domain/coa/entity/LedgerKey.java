package com.maya.cbs.ledger.domain.coa.entity;

import com.maya.cbs.ledger.domain.base.Currency;
import lombok.Value;

@Value
public class LedgerKey {

    GeneralLedgerAccountCode code;
    Currency currency;
}
