package com.maya.cbs.ledger.domain.posting.entity;

import com.maya.cbs.ledger.domain.base.EntryDirection;
import lombok.Data;

@Data
public class LegTemplate {

    EntryDirection direction;
    AmountComponent amountComponent;
}
