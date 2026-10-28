package com.maya.cbs.ledger.domain.account.persistence;

@FunctionalInterface
public interface AccountNumberSequence {

    long next ();
}
