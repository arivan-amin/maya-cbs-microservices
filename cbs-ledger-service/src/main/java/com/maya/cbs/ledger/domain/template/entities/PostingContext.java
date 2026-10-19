package com.maya.cbs.ledger.domain.template.entities;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostingContext {

    private String id;
    private String transactionType;
    private BigDecimal amount;
    private String currency;
    private String sourceAccountId;
    private String destinationAccountId;
    private Map<String, Object> metadata;
}
