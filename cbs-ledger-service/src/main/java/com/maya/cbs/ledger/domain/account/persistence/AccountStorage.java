package com.maya.cbs.ledger.domain.account.persistence;

import com.maya.cbs.ledger.domain.account.entity.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountStorage {

    Optional<Account> findById (UUID accountId);

    Optional<Account> findByAccountNumber (String accountNumber);

    void save (Account account);
}
