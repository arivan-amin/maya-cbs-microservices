package com.maya.cbs.ledger.domain.account.entity;

import com.maya.cbs.ledger.domain.account.exception.*;
import com.maya.cbs.ledger.domain.base.*;
import lombok.Getter;
import lombok.ToString;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
public final class Account {

    private final UUID id;
    private final String accountNumber;
    private final String name;
    private final AccountType type;
    private final Currency currency;
    private final Instant openedAt;
    private final AccountBalance balance;
    private AccountStatus status;

    private Account (UUID id, String accountNumber, String name, AccountType type,
                     Currency currency, Instant openedAt, AccountStatus status,
                     AccountBalance balance) {
        this.id = Objects.requireNonNull(id, "Account ID cannot be null");
        this.accountNumber = requireNotBlank(accountNumber, "Account number");
        this.name = requireNotBlank(name, "Account name");
        this.type = Objects.requireNonNull(type, "Account type cannot be null");
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");
        this.openedAt = Objects.requireNonNull(openedAt, "Opened-at cannot be null");
        this.status = Objects.requireNonNull(status, "Status cannot be null");
        this.balance = Objects.requireNonNull(balance, "Balance cannot be null");
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

    public static Account open (UUID id, String accountNumber, String name, AccountType type,
                                Currency currency, Clock clock) {
        Objects.requireNonNull(clock, "Clock cannot be null");
        return new Account(id, accountNumber, name, type, currency, clock.instant(),
            AccountStatus.ACTIVE, AccountBalance.open(id, currency));
    }

    public static Account restore (UUID id, String accountNumber, String name, AccountType type,
                                   Currency currency, Instant openedAt, AccountStatus status,
                                   AccountBalance balance) {
        return new Account(id, accountNumber, name, type, currency, openedAt, status, balance);
    }

    public void post (Money amount, EntryDirection direction) {
        Objects.requireNonNull(direction, "Direction cannot be null");
        requireStatusAllows(direction);
        if (direction == type.getNormalBalanceSide()) {
            balance.increase(amount);
        }
        else {
            balance.decrease(amount);
        }
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
