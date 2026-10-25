package com.maya.cbs.ledger.domain.account.entity;

import java.util.UUID;

@FunctionalInterface
public interface AccountSource {

    UUID resolve (AccountBindings bindings);
}
