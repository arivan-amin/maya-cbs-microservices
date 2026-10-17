package com.maya.cbs.ledger.domain.account.usecase;

import com.maya.cbs.ledger.domain.account.entity.Account;
import com.maya.cbs.ledger.domain.account.entity.AccountStatus;
import com.maya.cbs.ledger.domain.account.exception.InactiveAccountException;
import com.maya.cbs.ledger.domain.account.exception.JournalAccountNotFoundException;
import com.maya.cbs.ledger.domain.account.persistence.AccountStorage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountValidationUseCase {

    private final AccountStorage accountStorage;

    public void validate (String accountNumber) {
        Account account = accountStorage.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new JournalAccountNotFoundException(accountNumber));

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new InactiveAccountException(accountNumber);
        }
    }
}
