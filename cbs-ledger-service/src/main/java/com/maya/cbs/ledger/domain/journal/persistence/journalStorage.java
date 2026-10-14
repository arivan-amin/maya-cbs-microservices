package com.maya.cbs.ledger.domain.journal.persistence;

import com.maya.cbs.ledger.domain.journal.entity.Journal;

import java.util.Optional;
import java.util.UUID;

public interface journalStorage {

    Optional<Journal> findById (UUID journalId);

    void save (Journal journal);
}
