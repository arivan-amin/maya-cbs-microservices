package com.maya.cbs.ledger.domain.template.usecase;

import com.maya.cbs.ledger.domain.journal.entity.JournalEntry;
import com.maya.cbs.ledger.domain.template.entities.PostingContext;
import com.maya.cbs.ledger.domain.template.persistence.TemplateStorage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostingRuleEngine {

    private final TemplateStorage templateStorage;
    private final AmountExpressionEvaluator expressionEvaluator;
    private final AccountResolver accountResolver;

    JournalEntry evaluate (PostingContext context) {
        return null;
    }
}
