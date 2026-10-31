package com.maya.cbs.ledger.domain.coa.entity;

import com.maya.cbs.ledger.domain.base.Currency;
import com.maya.cbs.ledger.domain.transaction.entity.TransactionType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountingContext {

    TransactionType transactionType;
    Currency currency;
    CustomerSegment customerSegment;
    Channel channel;
}
