package com.maya.cbs.ledger.domain.posting;

import com.maya.cbs.ledger.domain.base.EntryDirection;
import com.maya.cbs.ledger.domain.base.Money;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.*;

@Value
public class PostingRule {

    UUID id;
    String code;
    int version;
    PostingRuleStatus status;
    PostingRuleSelector selector;
    List<LegTemplate> legs;

    @Builder
    private PostingRule (UUID id, String code, int version, PostingRuleStatus status,
                         PostingRuleSelector selector, LocalDate effectiveFrom,
                         LocalDate effectiveTo, List<LegTemplate> legs) {

        if (version < 1) {
            throw new IllegalArgumentException("Posting rule version must be at least 1");
        }
        if (effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new IllegalArgumentException("effectiveTo must not be before effectiveFrom");
        }
        if (!hasDirection(legs, EntryDirection.DEBIT) ||
            !hasDirection(legs, EntryDirection.CREDIT)) {
            throw new IllegalArgumentException(
                "Posting rule must have at least one debit and one credit leg");
        }

        this.id = id;
        this.code = code;
        this.version = version;
        this.status = status;
        this.selector = selector;
        this.legs = List.copyOf(legs);
    }

    private static boolean hasDirection (List<LegTemplate> legs, EntryDirection direction) {
        return legs.stream()
            .anyMatch(leg -> leg.getDirection() == direction);
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
