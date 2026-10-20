package com.maya.cbs.ledger.domain.journal.usecase;

import com.maya.cbs.ledger.domain.base.EntryDirection;
import com.maya.cbs.ledger.domain.journal.entity.JournalEntry;
import com.maya.cbs.ledger.domain.journal.entity.JournalEntryLine;
import com.maya.cbs.ledger.domain.journal.exception.UnbalancedJournalEntryException;

import java.math.BigDecimal;
import java.util.List;

public class DoubleEntryValidationUseCase {

    public void validate (JournalEntry journalEntry) {
        List<JournalEntryLine> lines = journalEntry.getLines();
        if (lines.size() < 2) {
            throw new IllegalArgumentException("Journal Entry must have at least two lines");
        }

        BigDecimal debitTotal = getLineTotal(lines, EntryDirection.DEBIT);
        BigDecimal creditTotal = getLineTotal(lines, EntryDirection.CREDIT);

        if (debitTotal.compareTo(creditTotal) != 0) {
            throw new UnbalancedJournalEntryException("Credit and debit are not balances");
        }
    }

    private BigDecimal getLineTotal (List<JournalEntryLine> lines, EntryDirection debit) {
        return lines.stream()
            .filter(journalEntryLine -> journalEntryLine.getDirection() == debit)
            .map(journalEntryLine1 -> journalEntryLine1.getMoney()
                .getAmount())
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
