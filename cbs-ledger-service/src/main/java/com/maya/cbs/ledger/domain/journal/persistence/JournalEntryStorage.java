package com.maya.cbs.ledger.domain.journal.persistence;

import com.maya.cbs.ledger.domain.journal.entity.JournalEntry;

import java.util.Optional;
import java.util.UUID;

public interface JournalEntryStorage {

    Optional<JournalEntry> findById (UUID journalEntryId);

    Optional<JournalEntry> findByReferenceId (String referenceId);

    void save (JournalEntry journalEntry);
}
