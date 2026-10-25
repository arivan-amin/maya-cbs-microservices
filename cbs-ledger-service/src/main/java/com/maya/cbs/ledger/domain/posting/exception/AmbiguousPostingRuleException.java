package com.maya.cbs.ledger.domain.posting.exception;

import com.maya.cbs.ledger.domain.coa.entity.AccountingContext;
import com.maya.cbs.ledger.domain.posting.entity.PostingRule;

import java.util.List;

public class AmbiguousPostingRuleException extends RuntimeException {

    public AmbiguousPostingRuleException (AccountingContext context, List<PostingRule> top) {
        super("ambiguous posting rule for %s, and the list rule: %s".formatted(context, top));
    }
}
