package com.maya.cbs.ledger.domain.base;

import java.math.BigDecimal;

public class InvalidMoneyException extends RuntimeException {

    public InvalidMoneyException (BigDecimal amount, Currency currency) {
        super(
            "Amount %s exceeds %s precision of %d fraction digits".formatted(amount.toPlainString(),
                currency, currency.getMaxAllowedFractionDigits()));
    }
}
