package com.maya.cbs.ledger.domain.coa.entity;

import lombok.Value;

import java.util.Objects;
import java.util.regex.Pattern;

@Value
public class GeneralLedgerAccountCode {

    private static final Pattern FORMAT = Pattern.compile("[1-9]\\d{3}");

    String value;

    private GeneralLedgerAccountCode (String value) {
        Objects.requireNonNull(value, "GL account code cannot be null");
        if (!FORMAT.matcher(value)
            .matches()) {
            throw new IllegalArgumentException(
                "GL account code must be 4 digits and not start with 0: " + value);
        }
        this.value = value;
    }

    public static GeneralLedgerAccountCode of (String value) {
        return new GeneralLedgerAccountCode(value);
    }

    public int classDigit () {
        return value.charAt(0) - '0';
    }

    @Override
    public String toString () {
        return value;
    }
}
