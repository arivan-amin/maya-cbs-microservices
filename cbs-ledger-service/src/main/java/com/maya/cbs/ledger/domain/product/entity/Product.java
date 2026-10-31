package com.maya.cbs.ledger.domain.product.entity;

import com.maya.cbs.ledger.domain.base.Currency;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public final class Product {

    private final UUID id;
    private final String code;
    private final String name;
    private final String description;
    private final ProductType type;
    private final Currency currency;
    private final ProductStatus status;
    private final Long version;
    private final Instant createdAt;
    private final Instant createdBy;
}
