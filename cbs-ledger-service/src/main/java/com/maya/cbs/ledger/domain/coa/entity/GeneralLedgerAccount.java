package com.maya.cbs.ledger.domain.coa.entity;

import com.maya.cbs.ledger.domain.base.Currency;
import com.maya.cbs.ledger.domain.base.Money;
import com.maya.cbs.ledger.domain.coa.exceptions.GeneralLedgerAccountException;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public final class GeneralLedgerAccount {

    private final UUID id;
    private final GeneralLedgerAccountCode code;
    private final String name;
    private final GeneralLedgerAccountType type;
    private final Currency currency;
    private final UUID parentId;
    private final boolean postingAllowed;
    private final Long version;
    private GeneralLedgerAccountStatus status;

    private GeneralLedgerAccount (UUID id, GeneralLedgerAccountCode code, String name,
                                  GeneralLedgerAccountType type, Currency currency, UUID parentId,
                                  boolean postingAllowed, GeneralLedgerAccountStatus status,
                                  Long version) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.code = Objects.requireNonNull(code, "Code cannot be null");
        this.name = requireNotBlank(name);
        this.type = Objects.requireNonNull(type, "Type cannot be null");
        this.currency = Objects.requireNonNull(currency, "Currency cannot be null");
        this.parentId = parentId;
        this.postingAllowed = postingAllowed;
        this.status = Objects.requireNonNull(status, "Status cannot be null");
        this.version = version;
        requireCodeMatchesType();
    }

    private static String requireNotBlank (String value) {
        Objects.requireNonNull(value, "Name cannot be null");
        if (value.isBlank()) {
            throw new GeneralLedgerAccountException("Name cannot be blank");
        }
        return value;
    }

    private void requireCodeMatchesType () {
        if (code.classDigit() != type.getClassDigit()) {
            throw new GeneralLedgerAccountException(
                "Code %s does not belong to class %s".formatted(code, type));
        }
    }

    public static GeneralLedgerAccount open (UUID id, GeneralLedgerAccountCode code, String name,
                                             GeneralLedgerAccountType type, Currency currency,
                                             UUID parentId, boolean postingAllowed) {
        return new GeneralLedgerAccount(id, code, name, type, currency, parentId, postingAllowed,
            GeneralLedgerAccountStatus.ACTIVE, null);
    }

    public static GeneralLedgerAccount restore (UUID id, GeneralLedgerAccountCode code, String name,
                                                GeneralLedgerAccountType type, Currency currency,
                                                UUID parentId, boolean postingAllowed,
                                                GeneralLedgerAccountStatus status, Long version) {
        return new GeneralLedgerAccount(id, code, name, type, currency, parentId, postingAllowed,
            status, version);
    }

    public void validateCanPost (Money amount) {
        Objects.requireNonNull(amount, "Amount cannot be null");
        if (isHeader()) {
            throw new GeneralLedgerAccountException(
                "Posting prohibited: %s (%s) is a header account".formatted(code, name));
        }
        if (!status.allowsPosting()) {
            throw new GeneralLedgerAccountException(
                "Posting prohibited: %s (%s) is %s".formatted(code, name, status));
        }
        if (currency != amount.getCurrency()) {
            throw new GeneralLedgerAccountException(
                "Currency mismatch on %s: account is %s but amount is %s".formatted(code, currency,
                    amount.getCurrency()));
        }
    }

    public boolean isHeader () {
        return !postingAllowed;
    }

    public void deactivate () {
        if (status == GeneralLedgerAccountStatus.INACTIVE) {
            throw new GeneralLedgerAccountException(code + " is already inactive");
        }
        status = GeneralLedgerAccountStatus.INACTIVE;
    }

    public void activate () {
        if (status == GeneralLedgerAccountStatus.ACTIVE) {
            throw new GeneralLedgerAccountException(code + " is already active");
        }
        status = GeneralLedgerAccountStatus.ACTIVE;
    }
}
