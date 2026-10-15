package com.maya.cbs.ledger.domain.journal.usecase;

import com.maya.cbs.ledger.domain.journal.persistence.JournalEntryStorage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JournalPostingUseCase {

    private final JournalEntryStorage journalEntryStorage;
}
