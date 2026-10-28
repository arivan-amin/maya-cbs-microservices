package com.maya.cbs.ledger.domain.account.entity;

import lombok.Value;

import java.util.Objects;
import java.util.regex.Pattern;

@Value
public class AccountNumber {

    private static final int BODY_LENGTH = 11;
    private static final int LENGTH = BODY_LENGTH + 1;
    private static final long MAX_SEQUENCE = 99_999_999_999L;
    private static final int VISIBLE_DIGITS = 4;
    private static final Pattern FORMAT = Pattern.compile("\\d{" + LENGTH + "}");

    String value;

    private AccountNumber (String value) {
        this.value = value;
    }

    public static AccountNumber fromSequence (long sequence) {
        if (sequence < 1 || sequence > MAX_SEQUENCE) {
            throw new IllegalArgumentException(
                "Account number sequence must be between 1 and " + MAX_SEQUENCE);
        }
        String body = String.format("%0" + BODY_LENGTH + "d", sequence);
        return new AccountNumber(body + checkDigit(body));
    }

    private static int checkDigit (String body) {
        int sum = 0;
        boolean doubleDigit = true;
        for (int i = body.length() - 1; i >= 0; i--) {
            int digit = body.charAt(i) - '0';
            if (doubleDigit) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            doubleDigit = !doubleDigit;
        }
        return (10 - sum % 10) % 10;
    }

    public static AccountNumber of (String value) {
        Objects.requireNonNull(value, "Account number cannot be null");
        if (!FORMAT.matcher(value)
            .matches()) {
            throw new IllegalArgumentException(
                "Account number must be exactly " + LENGTH + " digits");
        }
        String body = value.substring(0, BODY_LENGTH);
        int expected = checkDigit(body);
        int actual = value.charAt(BODY_LENGTH) - '0';
        if (expected != actual) {
            throw new IllegalArgumentException("Account number has an invalid check digit");
        }
        return new AccountNumber(value);
    }

    @Override
    public String toString () {
        return masked();
    }

    public String masked () {
        int hidden = LENGTH - VISIBLE_DIGITS;
        return "*".repeat(hidden) + value.substring(hidden);
    }
}
