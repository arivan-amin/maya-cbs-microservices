package com.maya.cbs.ledger.domain.journal.entity;

import com.maya.cbs.ledger.domain.base.EntryDirection;
import com.maya.cbs.ledger.domain.base.Money;
import lombok.*;

import java.util.UUID;

@Getter
@ToString
@Builder
public class JournalEntryLine {

    private UUID id;
    private UUID journalEntryId;
    private UUID glAccountId;
    private UUID customerAccountId;
    private EntryDirection direction;
    private Money money;
    private UUID productId;
}
