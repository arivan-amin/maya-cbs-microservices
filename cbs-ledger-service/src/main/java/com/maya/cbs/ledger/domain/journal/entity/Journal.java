package com.maya.cbs.ledger.domain.journal.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Journal {

    private UUID id;
    private String journalName;
    private String description;
    private LocalDateTime createdAt;
}
