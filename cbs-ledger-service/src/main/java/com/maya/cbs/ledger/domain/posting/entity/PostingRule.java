package com.maya.cbs.ledger.domain.posting.entity;

import com.maya.cbs.ledger.domain.account.entity.AccountBindings;
import com.maya.cbs.ledger.domain.base.EntryDirection;
import com.maya.cbs.ledger.domain.base.Money;
import lombok.Getter;

import java.util.*;

@Getter
public final class PostingRule {

    UUID id;
    String code;
    PostingRuleStatus status;
    PostingRuleSelector selector;
    List<LegTemplate> legs;

    private PostingRule (UUID id, String code, PostingRuleStatus status,
                         PostingRuleSelector selector, List<LegTemplate> legs) {
        if (!hasDirection(legs, EntryDirection.DEBIT) ||
            !hasDirection(legs, EntryDirection.CREDIT)) {
            throw new IllegalArgumentException(
                "Posting rule must have at least one debit and one credit leg");
        }

        this.id = id;
        this.code = code;
        this.status = status;
        this.selector = selector;
        this.legs = List.copyOf(legs);
    }

    private boolean hasDirection (List<LegTemplate> legs, EntryDirection direction) {
        return legs.stream()
            .anyMatch(leg -> leg.getDirection() == direction);
    }

    public static PostingRule createPostingRule (UUID id, String code, PostingRuleStatus status,
                                                  PostingRuleSelector selector,
                                                  List<LegTemplate> legs) {
        return new PostingRule(id, code, status, selector, legs);
    }

    public boolean isActive () {
        return status == PostingRuleStatus.ACTIVE;
    }

    public List<ResolvedLeg> instantiate (AmountBreakdown amounts, AccountBindings bindings) {
        List<ResolvedLeg> resolvedLegs = new ArrayList<>();
        for (LegTemplate leg : legs) {
            Money amount = amounts.get(leg.getAmountComponent());
            if (amount.isZero()) {
                continue;
            }
            resolvedLegs.add(leg.resolve(amount, bindings));
        }
        return List.copyOf(resolvedLegs);
    }
}
