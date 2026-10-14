package com.maya.cbs.ledger.domain.journal.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntry {

    private UUID id;
    private UUID journalId;
    private LocalDateTime transactionTimestamp;
    private LocalDate postingDate;
    private JournalStatus status;
    private String referenceId;
    private String description;
    private List<JournalEntryLine> lines;
}
