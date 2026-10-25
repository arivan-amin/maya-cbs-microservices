package com.maya.cbs.ledger.domain.posting.entity;

import com.maya.cbs.ledger.domain.base.Money;
import com.maya.cbs.ledger.domain.posting.exception.MissingAmountComponentException;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor (access = AccessLevel.PRIVATE)
public final class AmountBreakdown {

    Map<AmountComponent, Money> components;

    public static AmountBreakdown forPurchase (Money principal, Money fee, Money tax) {
        Map<AmountComponent, Money> components = new HashMap<>();
        components.put(AmountComponent.PRINCIPAL, principal);
        components.put(AmountComponent.FEE, fee);
        components.put(AmountComponent.TAX, tax);
        components.put(AmountComponent.GROSS, principal.plus(fee)
            .plus(tax));
        return new AmountBreakdown(components);
    }

    public Money get (AmountComponent component) {
        Money amount = components.get(component);
        if (amount == null) {
            throw new MissingAmountComponentException(component);
        }
        return amount;
    }
}
