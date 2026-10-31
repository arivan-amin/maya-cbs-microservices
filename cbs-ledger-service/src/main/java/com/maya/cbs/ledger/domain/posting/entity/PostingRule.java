package com.maya.cbs.ledger.domain.posting.entity;

import com.maya.cbs.ledger.domain.base.Currency;
import com.maya.cbs.ledger.domain.base.EntryDirection;
import com.maya.cbs.ledger.domain.coa.entity.Channel;
import com.maya.cbs.ledger.domain.coa.entity.CustomerSegment;
import com.maya.cbs.ledger.domain.transaction.entity.TransactionType;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public final class PostingRule {

    UUID id;
    String code;
    String name;
    TransactionType transactionType;
    PostingRuleStatus status;
    Currency currency;
    CustomerSegment segment;
    Channel channel;
    List<PostingRuleLine> lines;

    private PostingRule (UUID id, String code, String name, TransactionType transactionType,
                         PostingRuleStatus status, List<PostingRuleLine> lines) {
        if (!hasDirection(lines, EntryDirection.DEBIT) ||
            !hasDirection(lines, EntryDirection.CREDIT)) {
            throw new IllegalArgumentException(
                "Posting rule must have at least one debit and one credit leg");
        }

        this.id = id;
        this.code = code;
        this.name = name;
        this.transactionType = transactionType;
        this.status = status;
        this.lines = List.copyOf(lines);
    }

    private boolean hasDirection (List<PostingRuleLine> legs, EntryDirection direction) {
        return legs.stream()
            .anyMatch(leg -> leg.getDirection() == direction);
    }

    public static PostingRule createPostingRule (UUID id, String code, String name,
                                                 TransactionType transactionType,
                                                 PostingRuleStatus status,
                                                 List<PostingRuleLine> legs) {
        return new PostingRule(id, code, name, transactionType, status, legs);
    }

    public boolean isActive () {
        return status == PostingRuleStatus.ACTIVE;
    }
}
