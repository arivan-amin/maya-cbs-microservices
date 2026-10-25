package com.maya.cbs.ledger.domain.posting.entity;

import com.maya.cbs.ledger.domain.base.EntryDirection;
import com.maya.cbs.ledger.domain.base.Money;
import lombok.Value;

import java.util.UUID;

@Value
public class ResolvedLeg {

    EntryDirection direction;
    UUID generalLedgerId;
    Money amount;
}
