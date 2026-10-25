package com.maya.cbs.ledger.domain.posting.entity;

import com.maya.cbs.ledger.domain.account.entity.AccountBindings;
import com.maya.cbs.ledger.domain.account.entity.AccountSource;
import com.maya.cbs.ledger.domain.base.EntryDirection;
import com.maya.cbs.ledger.domain.base.Money;
import lombok.Value;

@Value
public class LegTemplate {

    EntryDirection direction;
    AmountComponent amountComponent;
    AccountSource accountSource;

    public static LegTemplate debit (AmountComponent component, AccountSource source) {
        return new LegTemplate(EntryDirection.DEBIT, component, source);
    }

    public static LegTemplate credit (AmountComponent component, AccountSource source) {
        return new LegTemplate(EntryDirection.CREDIT, component, source);
    }

    public ResolvedLeg resolve (Money amount, AccountBindings bindings) {
        return new ResolvedLeg(direction, accountSource.resolve(bindings), amount);
    }
}
