package com.maya.cbs.ledger.domain.template.entities;

import com.maya.cbs.ledger.domain.base.EntryDirection;

public class PostingLeg {

    private EntryDirection direction;
    private AccountTargetType type;
    private String reference;
    private AmountType amountType;
    private String expression;

    public void validate () {
        if (type == AccountTargetType.GENERAL_LEDGER && reference == null) {
            throw new IllegalArgumentException(
                "General Ledger legs must specify a target account reference code.");
        }
        if (amountType == AmountType.EXPRESSION && (expression == null || expression.isBlank())) {
            throw new IllegalArgumentException(
                "AmountType is set to EXPRESSION, but no expression string was provided.");
        }
    }
}
