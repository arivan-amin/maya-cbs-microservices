package com.maya.cbs.ledger.domain.posting;

import com.maya.cbs.ledger.domain.base.Money;
import lombok.*;

import java.util.Map;

@Value
@Builder
public class AmountBreakdown {

    @Singular
    Map<AmountComponent, Money> components;

    public static AmountBreakdown forPurchase (Money principal, Money fee, Money tax) {
        return builder()
            .component(AmountComponent.PRINCIPAL, principal)
            .component(AmountComponent.FEE, fee)
            .component(AmountComponent.TAX, tax)
            .component(AmountComponent.GROSS, principal.plus(fee)
                .plus(tax))
            .build();
    }

    public Money get (AmountComponent component) {
        Money amount = components.get(component);
        if (amount == null) {
            throw new MissingAmountComponentException(component);
        }
        return amount;
    }
}
