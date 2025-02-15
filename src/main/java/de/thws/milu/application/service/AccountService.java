package de.thws.milu.application.service;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaAccount;
import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.port.in.AccountServicePort;
import de.thws.milu.core.port.out.AccountRepositoryPort;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountService implements AccountServicePort {

    private final AccountRepositoryPort accountRepository;

    @Inject
    public AccountService(AccountRepositoryPort accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> getById(UUID id) {
        return accountRepository.getById(id);
    }

    @Override
    public List<Account> getAll(String name, int limit, int offset) {
        if (name != null && !name.trim().isEmpty()) {
            return accountRepository.findByName(name, limit, offset).stream().map(a -> (Account) a).toList();
        } else {
            return accountRepository.getAll(limit, offset).stream().map(a -> (Account) a).toList();
        }
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void update(UUID id, JpaAccount account) {
        accountRepository.update(id, account);
    }

    @Override
    public void deleteById(UUID id) {
        accountRepository.deleteById(id);
    }
}
