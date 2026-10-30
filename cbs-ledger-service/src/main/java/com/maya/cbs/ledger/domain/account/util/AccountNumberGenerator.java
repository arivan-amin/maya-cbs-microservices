package com.maya.cbs.ledger.domain.account.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class AccountNumberGenerator {

    private static final int BODY_LENGTH = 11;
    private static final int LENGTH = BODY_LENGTH + 1;
    private static final long MAX_SEQUENCE = 99_999_999_999L;

    public static String fromSequence (long sequence) {
        if (sequence < 1 || sequence > MAX_SEQUENCE) {
            throw new IllegalArgumentException(
                "Account number sequence must be between 1 and " + MAX_SEQUENCE);
        }
        String body = String.format("%0" + BODY_LENGTH + "d", sequence);
        return body + checkDigit(body);
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
}
