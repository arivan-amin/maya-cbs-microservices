package com.maya.cbs.ledger.domain.posting;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Value
@Builder
public class AccountBindings {

    @Singular
    Map<AccountRole, UUID> bindings;

    public UUID glAccountFor (AccountRole role) {
        UUID glAccountId = bindings.get(role);
        if (glAccountId == null) {
            throw new MissingAccountBindingException(role);
        }
        return glAccountId;
    }
}
