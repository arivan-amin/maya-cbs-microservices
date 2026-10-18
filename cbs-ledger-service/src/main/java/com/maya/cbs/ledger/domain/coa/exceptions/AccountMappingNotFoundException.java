package com.maya.cbs.ledger.domain.coa.exceptions;

import com.maya.cbs.ledger.domain.coa.entity.AccountingContext;

public class AccountMappingNotFoundException extends RuntimeException {

    public AccountMappingNotFoundException (AccountingContext context) {
        super("General ledger mapping not found for: %s".formatted(context));
    }
}
