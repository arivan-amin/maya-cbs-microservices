package com.maya.cbs.ledger.domain.posting;

import com.maya.cbs.ledger.domain.base.EntryDirection;
import com.maya.cbs.ledger.domain.base.Money;
import lombok.Value;

import java.util.UUID;

@Value
public class ResolvedLeg {

    EntryDirection direction;
    UUID glAccountId;
    Money amount;
}
