package com.maya.cbs.ledger.domain.coa.entity;

import com.maya.cbs.ledger.domain.account.entity.AccountStatus;
import com.maya.cbs.ledger.domain.base.Currency;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralLedgerAccount {

    private UUID id;
    private UUID parentId;
    private String accountNumber;
    private String name;
    private GeneralLedgerAccountType type;
    private Currency currency;
    private List<GeneralLedgerAccount> children;
    private boolean isPostingAllowed;
    private AccountStatus status;
    private Long version;

    public void validateCanPost () {
        if (!isPostingAllowed) {
            throw new IllegalStateException(
                String.format("Posting prohibited: Account %s (%s) is a control account.",
                    accountNumber, name));
        }
        if (status != AccountStatus.ACTIVE) {
            throw new IllegalStateException(
                String.format("Posting prohibited: Account %s (%s) is %s.", accountNumber, name,
                    status));
        }
    }
}
