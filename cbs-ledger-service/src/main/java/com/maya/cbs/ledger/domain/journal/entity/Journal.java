package com.maya.cbs.ledger.domain.journal.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@ToString
@Builder
public final class Journal {

    private UUID id;
    private String journalName;
    private String description;
    private LocalDateTime createdAt;
}
