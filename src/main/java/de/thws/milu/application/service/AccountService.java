package de.thws.milu.application.service;

import de.thws.milu.core.domain.model.Account;
import de.thws.milu.application.port.out.AccountRepository;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountService {

    private final AccountRepository accountRepository;

    @Inject
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> getById(UUID id) {
        return accountRepository.getById(id);
    }

    public List<Account> getAll() {
        return accountRepository.getAll().stream().map(a -> (Account) a).toList();
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void deleteById(UUID id) {
        accountRepository.deleteById(id);
    }
}
