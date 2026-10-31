package com.maya.cbs.ledger.domain.journal.entity;

import com.maya.cbs.ledger.domain.base.EntryDirection;
import com.maya.cbs.ledger.domain.journal.exception.UnbalancedJournalEntryException;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@ToString
public final class JournalEntry {

    private UUID id;
    private Instant timestamp;
    private LocalDate postingDate;
    private String referenceId;
    private String idempotencyKey;
    private String description;
    private UUID reversalOf;
    private List<JournalEntryLine> lines;

    public static JournalEntry of (UUID id, Instant timestamp, LocalDate postingDate,
                                   String referenceId, String idempotencyKey, String description,
                                   UUID reversalOf, List<JournalEntryLine> lines) {
        validate(lines);
        return new JournalEntry(id, timestamp, postingDate, referenceId, idempotencyKey,
            description, reversalOf, lines);
    }

    public static void validate (List<JournalEntryLine> lines) {
        if (lines.size() < 2) {
            throw new IllegalArgumentException("Journal Entry must have at least two lines");
        }

        BigDecimal debitTotal = getLineTotal(lines, EntryDirection.DEBIT);
        BigDecimal creditTotal = getLineTotal(lines, EntryDirection.CREDIT);

        if (debitTotal.compareTo(creditTotal) != 0) {
            throw new UnbalancedJournalEntryException("Credit and debit are not balances");
        }
    }

    private static BigDecimal getLineTotal (List<JournalEntryLine> lines, EntryDirection debit) {
        return lines.stream()
            .filter(journalEntryLine -> journalEntryLine.getDirection() == debit)
            .map(journalEntryLine1 -> journalEntryLine1.getMoney()
                .getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
