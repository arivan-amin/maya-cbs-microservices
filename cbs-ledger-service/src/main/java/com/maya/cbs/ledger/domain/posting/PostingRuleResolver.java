package com.maya.cbs.ledger.domain.posting;

import com.maya.cbs.ledger.domain.coa.entity.AccountingContext;

import java.util.List;

public class PostingRuleResolver {

    public PostingRule resolve (List<PostingRule> candidates, AccountingContext context) {

        List<PostingRule> matching = candidates.stream()
            .filter(rule -> rule.getStatus() == PostingRuleStatus.ACTIVE)
            .filter(rule -> rule.getSelector()
                .matches(context.getCustomerSegment()))
            .toList();

        if (matching.isEmpty())
            throw new NoPostingRuleFoundException(context);

        int best = matching.stream()
            .mapToInt(r -> r.getSelector()
                .specificity())
            .max()
            .getAsInt();
        List<PostingRule> top = matching.stream()
            .filter(r -> r.getSelector()
                             .specificity() == best)
            .toList();

        if (top.size() > 1)
            throw new AmbiguousPostingRuleException(context, top);
        return top.get(0);
    }
}
