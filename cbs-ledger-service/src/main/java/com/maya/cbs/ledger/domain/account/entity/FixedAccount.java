package com.maya.cbs.ledger.domain.account.entity;

import lombok.Value;

import java.util.UUID;

@Value
public class FixedAccount implements AccountSource {

    UUID glAccountId;

    public UUID resolve (AccountBindings bindings) {
        return glAccountId;
    }
}
