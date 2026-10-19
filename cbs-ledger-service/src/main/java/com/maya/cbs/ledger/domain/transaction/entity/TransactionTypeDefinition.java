package com.maya.cbs.ledger.domain.transaction.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionTypeDefinition {

    private String code;
    private String name;
    private String description;
    private boolean active;
}
