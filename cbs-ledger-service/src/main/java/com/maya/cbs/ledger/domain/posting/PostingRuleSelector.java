package com.maya.cbs.ledger.domain.posting;

import com.maya.cbs.ledger.domain.base.Currency;
import com.maya.cbs.ledger.domain.coa.entity.*;
import lombok.*;

@Value
@Builder
public class PostingRuleSelector {

    @NonNull
    FinancialMovementType movementType;
    Currency currency;
    CustomerSegment segment;
    Channel channel;

    public boolean matches (AccountingContext context) {
        return movementType == context.getType() &&
               matchesCriterion(currency, context.getCurrency()) &&
               matchesCriterion(segment, context.getCustomerSegment()) &&
               matchesCriterion(channel, context.getChannel());
    }

    private <T> boolean matchesCriterion (T ruleCriterion, T contextValue) {
        return ruleCriterion == null || ruleCriterion.equals(contextValue);
    }

    public int specificity () {
        int specificity = 0;
        if (currency != null)
            specificity++;
        if (segment != null)
            specificity++;
        if (channel != null)
            specificity++;
        return specificity;
    }
}
