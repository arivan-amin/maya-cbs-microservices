package com.maya.cbs.ledger.domain.transaction.entity;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public final class Transaction {

    private final UUID id;
    private final String externalReference;
    private final String idempotencyKey;
    private final String description;
    private final TransactionType type;
    private final TransactionStatus status;
    private final TransactionSource source;
    private final Instant initiatedAt;
    private final UUID initiatedBy;
    private final Instant completedAt;
    private final Long version;
}
