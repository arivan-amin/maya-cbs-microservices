package com.maya.cbs.ledger.domain.coa.entity;

import com.maya.cbs.ledger.domain.base.Currency;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountingContext {

    FinancialMovementType type;
    Currency currency;
    CustomerSegment customerSegment;
    Channel channel;
}
