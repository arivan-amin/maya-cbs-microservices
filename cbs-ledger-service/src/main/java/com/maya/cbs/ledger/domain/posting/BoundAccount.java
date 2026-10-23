package com.maya.cbs.ledger.domain.posting;

import lombok.Value;

import java.util.UUID;

@Value
public class BoundAccount implements LegAccountSource {

    AccountRole role;

    public UUID resolve (AccountBindings bindings) {
        return bindings.glAccountFor(role);
    }
}
