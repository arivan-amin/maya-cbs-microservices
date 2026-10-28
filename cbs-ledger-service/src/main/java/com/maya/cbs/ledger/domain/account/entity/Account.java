package com.maya.cbs.ledger.domain.account.entity;

import com.maya.cbs.ledger.domain.account.exception.*;
import com.maya.cbs.ledger.domain.base.*;
import lombok.*;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public final class Account {

    private final UUID id;
    private final String accountNumber;
    private final String name;
    private final Currency currency;
    private final Instant openedAt;
    private final AccountBalance balance;
    private final Long version;
    private AccountStatus status;

    private Account (UUID id, String accountNumber, String name, Currency currency,
                     Instant openedAt, AccountStatus status, AccountBalance balance, Long version) {
        this.id = Objects.requireNonNull(id, "Account ID cannot be null");
        this.accountNumber = requireNotBlank(accountNumber, "Account number");
        this.name = requireNotBlank(name, "Account name");
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");
        this.openedAt = Objects.requireNonNull(openedAt, "Opened-at cannot be null");
        this.status = Objects.requireNonNull(status, "Status cannot be null");
        this.balance = Objects.requireNonNull(balance, "Balance cannot be null");
        this.version = version;
        requireConsistentBalance(balance);
    }

    private static String requireNotBlank (String value, String label) {
        Objects.requireNonNull(value, label + " cannot be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException(label + " cannot be blank");
        }
        return value;
    }

    private void requireConsistentBalance (AccountBalance candidate) {
        if (!id.equals(candidate.getAccountId())) {
            throw new IllegalArgumentException("Balance belongs to a different account");
        }
        if (currency != candidate.getCurrency()) {
            throw new IllegalArgumentException("Balance currency does not match account currency");
        }
    }

    public static Account create (UUID id, String accountNumber, String name, Currency currency,
                                  Clock clock) {
        Objects.requireNonNull(clock, "Clock cannot be null");
        return new Account(id, accountNumber, name, currency, clock.instant(), AccountStatus.ACTIVE,
            AccountBalance.create(id, currency), null);
    }

    public static Account hydrate (UUID id, String accountNumber, String name,
                                   Currency currency, Instant openedAt, AccountStatus status,
                                   AccountBalance balance, Long version) {
        return new Account(id, accountNumber, name, currency, openedAt, status, balance, version);
    }

    public void post (Money amount, EntryDirection direction) {
        Objects.requireNonNull(direction, "Direction cannot be null");
        switch (direction) {
            case CREDIT -> credit(amount);
            case DEBIT -> debit(amount);
        }
    }

    public void credit (Money amount) {
        requireStatusAllows(EntryDirection.CREDIT);
        balance.increase(amount);
    }

    public void debit (Money amount) {
        requireStatusAllows(EntryDirection.DEBIT);
        balance.decrease(amount);
    }

    private void requireStatusAllows (EntryDirection direction) {
        if (!status.allows(direction)) {
            throw new AccountOperationNotAllowedException(id, status, direction);
        }
    }

    public void placeHold (Money amount) {
        requireStatusAllows(EntryDirection.DEBIT);
        balance.placeHold(amount);
    }

    public void releaseHold (Money amount) {
        balance.releaseHold(amount);
    }

    public void captureHold (Money holdAmount, Money captureAmount) {
        requireStatusAllows(EntryDirection.DEBIT);
        balance.captureHold(holdAmount, captureAmount);
    }

    public void changeOverdraftLimit (Money newLimit) {
        if (status == AccountStatus.CLOSED) {
            throw new AccountOperationNotAllowedException(id, status, EntryDirection.DEBIT);
        }
        balance.changeOverdraftLimit(newLimit);
    }

    public void freeze () {
        requireTransitionTo(AccountStatus.FROZEN);
        status = AccountStatus.FROZEN;
    }

    private void requireTransitionTo (AccountStatus target) {
        if (!status.canTransitionTo(target)) {
            throw new InvalidAccountStatusTransitionException(status, target);
        }
    }

    public void unfreeze () {
        requireTransitionTo(AccountStatus.ACTIVE);
        status = AccountStatus.ACTIVE;
    }

    public void close () {
        requireTransitionTo(AccountStatus.CLOSED);
        if (!balance.isFullySettled()) {
            throw new AccountNotSettledException(id);
        }
        status = AccountStatus.CLOSED;
    }
}
