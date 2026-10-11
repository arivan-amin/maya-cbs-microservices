package com.maya.cbs.ledger.domain.journal;

import com.maya.cbs.ledger.domain.base.Money;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Data
@NoArgsConstructor (access = AccessLevel.PRIVATE)
public class LedgerEntry {

    UUID id;
    UUID transactionId;
    UUID accountId;
    EntryDirection direction;
    Money amount;
    Instant createdAt;

    public LedgerEntry (UUID accountId, Money amount, EntryDirection direction) {
        requireNonNull(transactionId, "transactionId");
        requireNonNull(accountId, "accountId");
        requireNonNull(amount, "money");
        requireNonNull(direction, "direction");
        if (!amount.isPositive()) {
            throw new InvalidLedgerEntryException("Ledger entry amount must be greater than zero");
        }
        this.accountId = accountId;
        this.amount = amount;
        this.direction = direction;
    }

    public boolean isDebit () {
        return direction == EntryDirection.DEBIT;
    }

    public boolean isCredit () {
        return direction == EntryDirection.CREDIT;
    }
}
