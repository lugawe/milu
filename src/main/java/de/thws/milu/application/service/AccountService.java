package de.thws.milu.application.service;

import de.thws.milu.domain.model.Account;
import de.thws.milu.domain.repository.AccountRepository;
import jakarta.inject.Inject;
import java.util.List;

public class AccountService {

    private final AccountRepository accountRepository;

    @Inject
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccounts() {
        return accountRepository.getAll().stream().map(a -> (Account) a).toList();
    }
}
