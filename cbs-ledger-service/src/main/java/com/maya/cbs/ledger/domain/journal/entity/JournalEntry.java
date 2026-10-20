package com.maya.cbs.ledger.domain.journal.entity;

import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@ToString
@Builder
public class JournalEntry {

    private UUID id;
    private UUID journalId;
    private Instant timestamp;
    private LocalDate postingDate;
    private JournalStatus status;
    private String referenceId;
    private String description;
    private UUID reversalOf;
    private List<JournalEntryLine> lines;
}
