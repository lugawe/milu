package de.thws.milu.application.service;

import de.thws.milu.adapter.in.json.JsonAccount;
import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.port.in.AccountServicePort;
import de.thws.milu.core.port.out.AccountRepositoryPort;
import de.thws.milu.util.jwt.JwtHandler;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AccountService implements AccountServicePort {

    private final AccountRepositoryPort accountRepository;
    private final JwtHandler jwtHandler;

    @Inject
    public AccountService(AccountRepositoryPort accountRepository, JwtHandler jwtHandler) {
        this.accountRepository = accountRepository;
        this.jwtHandler = jwtHandler;
    }

    @Override
    public Optional<Account> getById(UUID id) {
        return accountRepository.getById(id);
    }

    @Override
    public Optional<Account> getByNameAndPassword(String name, String plainPassword) {

        Optional<Account> accountOptional = accountRepository.getByName(name);

        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();

            //if (BCryptUtils.check(plainPassword, account.getPassword())) {
            return Optional.of(account);
        }

        return Optional.empty();
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
    public void update(UUID id, JsonAccount account) {
        accountRepository.update(id, account);
    }

    @Override
    public void deleteById(UUID id) {
        accountRepository.deleteById(id);
    }

    @Override
    public String createAccessToken(Account account) {
        return jwtHandler.encode(account);
    }
}
