package com.maya.cbs.ledger.domain.account.entity;

import lombok.Value;

import java.util.UUID;

@Value
public class BoundAccount implements AccountSource {

    AccountRole role;

    public UUID resolve (AccountBindings bindings) {
        return bindings.glAccountFor(role);
    }
}
