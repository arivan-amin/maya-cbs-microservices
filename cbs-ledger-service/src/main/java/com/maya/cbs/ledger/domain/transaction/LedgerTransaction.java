package com.maya.cbs.ledger.domain.transaction;

import com.maya.cbs.ledger.domain.base.Currency;
import com.maya.cbs.ledger.domain.base.CurrencyMismatchException;
import com.maya.cbs.ledger.domain.journal.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LedgerTransaction {

    UUID id;
    String idempotencyKey;
    TransactionType type;
    Instant createdAt;
    List<LedgerEntry> entries;

    private static void validate (Currency currency, List<LedgerEntry> entries) {
        mustContainAtLeastTwoEntries(entries);
        mustContainBothDirections(entries);
        mustMatchCurrency(currency, entries);
        mustBeBalanced(entries);
    }

    private static void mustContainAtLeastTwoEntries (List<LedgerEntry> entries) {
        if (entries.size() < 2) {
            throw new InvalidLedgerEntryException(
                "Ledger transaction must contain at least two entries");
        }
    }

    private static void mustContainBothDirections (List<LedgerEntry> entries) {
        if (entries.stream()
            .noneMatch(LedgerEntry::isDebit)) {
            throw new InvalidLedgerEntryException(
                "Ledger transaction must contain at least one debit entry");
        }
        if (entries.stream()
            .noneMatch(LedgerEntry::isCredit)) {
            throw new InvalidLedgerEntryException(
                "Ledger transaction must contain at least one credit entry");
        }
    }

    private static void mustMatchCurrency (Currency currency, List<LedgerEntry> entries) {
        if (entries.stream()
            .anyMatch(entry -> entry.getAmount()
                                   .getCurrency() != currency)) {
            throw new CurrencyMismatchException(
                "Ledger entry currency must match ledger transaction currency");
        }
    }

    private static void mustBeBalanced (List<LedgerEntry> entries) {
        BigDecimal debits = total(entries, LedgerEntry::isDebit);
        BigDecimal credits = total(entries, LedgerEntry::isCredit);
        if (debits.compareTo(credits) != 0) {
            throw new UnbalancedLedgerException(
                "Ledger transaction debits and credits must be equal");
        }
    }

    private static BigDecimal total (List<LedgerEntry> entries, Predicate<LedgerEntry> side) {
        return entries.stream()
            .filter(side)
            .map(ledgerEntry -> ledgerEntry.getAmount()
                .getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
