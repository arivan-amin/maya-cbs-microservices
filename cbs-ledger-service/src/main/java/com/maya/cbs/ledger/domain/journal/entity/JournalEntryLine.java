package com.maya.cbs.ledger.domain.journal.entity;

import com.maya.cbs.ledger.domain.base.Currency;
import com.maya.cbs.ledger.domain.base.EntryDirection;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntryLine {

    private UUID id;
    private UUID journalEntryId;
    private UUID accountId;
    private EntryDirection direction;
    private BigDecimal amount;
    private Currency currency;
    private BigDecimal runningBalance;
}
