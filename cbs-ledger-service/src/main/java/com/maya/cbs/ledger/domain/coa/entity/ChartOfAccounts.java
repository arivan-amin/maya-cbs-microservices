package com.maya.cbs.ledger.domain.coa.entity;

import com.maya.cbs.ledger.domain.base.Currency;
import com.maya.cbs.ledger.domain.coa.exceptions.GeneralLedgerAccountException;

import java.util.*;

public class ChartOfAccounts {

    private final Map<UUID, GeneralLedgerAccount> byId = new HashMap<>();
    private final Map<LedgerKey, GeneralLedgerAccount> byKey = new HashMap<>();

    public static ChartOfAccounts empty () {
        return new ChartOfAccounts();
    }

    public static ChartOfAccounts hydrate (Collection<GeneralLedgerAccount> accounts) {
        ChartOfAccounts chart = new ChartOfAccounts();
        accounts.forEach(account -> {
            chart.requireUnique(account);
            chart.index(account);
        });
        accounts.forEach(chart::requireValidParent);
        return chart;
    }

    private void requireUnique (GeneralLedgerAccount account) {
        if (byId.containsKey(account.getId()) ||
            byKey.containsKey(new LedgerKey(account.getCode(), account.getCurrency()))) {
            throw new GeneralLedgerAccountException(
                "Duplicate GL account: " + account.getCode() + " " + account.getCurrency());
        }
    }

    private void index (GeneralLedgerAccount account) {
        byId.put(account.getId(), account);
        byKey.put(new LedgerKey(account.getCode(), account.getCurrency()), account);
    }

    private void requireValidParent (GeneralLedgerAccount account) {
        if (account.getParentId() == null) {
            return;
        }
        GeneralLedgerAccount parent = byId.get(account.getParentId());
        if (parent == null) {
            throw new GeneralLedgerAccountException(
                "Parent of " + account.getCode() + " does not exist");
        }
        if (!parent.isHeader()) {
            throw new GeneralLedgerAccountException(
                "Parent " + parent.getCode() + " must be a header account");
        }
        if (parent.getType() != account.getType()) {
            throw new GeneralLedgerAccountException(
                "Parent " + parent.getCode() + " has a different class than " + account.getCode());
        }
        if (parent.getCurrency() != account.getCurrency()) {
            throw new GeneralLedgerAccountException(
                "Parent " + parent.getCode() + " has a different currency than " +
                account.getCode());
        }
    }

    public void add (GeneralLedgerAccount account) {
        Objects.requireNonNull(account, "Account cannot be null");
        requireUnique(account);
        requireValidParent(account);
        index(account);
    }

    public void deactivate (UUID id) {
        GeneralLedgerAccount account = getById(id);
        boolean hasActiveChildren = children(id).stream()
            .anyMatch(child -> child.getStatus() == GeneralLedgerAccountStatus.ACTIVE);
        if (hasActiveChildren) {
            throw new GeneralLedgerAccountException(
                "Cannot deactivate " + account.getCode() + ": it has active children");
        }
        account.deactivate();
    }

    public GeneralLedgerAccount getById (UUID id) {
        GeneralLedgerAccount account = byId.get(id);
        if (account == null) {
            throw new GeneralLedgerAccountException("Unknown GL account: " + id);
        }
        return account;
    }

    public List<GeneralLedgerAccount> children (UUID parentId) {
        return byId.values()
            .stream()
            .filter(account -> parentId.equals(account.getParentId()))
            .sorted(Comparator.comparing(account -> account.getCode()
                .getValue()))
            .toList();
    }

    public void activate (UUID id) {
        GeneralLedgerAccount account = getById(id);
        if (account.getParentId() != null &&
            getById(account.getParentId()).getStatus() != GeneralLedgerAccountStatus.ACTIVE) {
            throw new GeneralLedgerAccountException(
                "Cannot activate " + account.getCode() + ": its parent is inactive");
        }
        account.activate();
    }

    public Optional<GeneralLedgerAccount> find (GeneralLedgerAccountCode code, Currency currency) {
        return Optional.ofNullable(byKey.get(new LedgerKey(code, currency)));
    }
}
