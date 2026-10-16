package com.maya.cbs.ledger.domain.journal.persistence;

import com.maya.cbs.ledger.domain.journal.entity.JournalEntryLine;

import java.util.List;
import java.util.UUID;

public interface JournalEntryLineStorage {

    List<JournalEntryLine> findByJournalEntryId (UUID journalEntryId);

    void saveAll (List<JournalEntryLine> lines);
}
