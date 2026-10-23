package com.maya.cbs.ledger.domain.posting;

import lombok.Value;

import java.util.UUID;

@Value
public class FixedAccount implements LegAccountSource {

    UUID glAccountId;

    public UUID resolve (AccountBindings bindings) {
        return glAccountId;
    }
}
