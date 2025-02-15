package de.thws.milu.core.port.out;

import de.thws.milu.core.domain.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepositoryPort {
    Optional<Account> getById(UUID id);

    Optional<Account> getByName(String name);

    List<? extends Account> getAll(int limit, int offset);

    List<? extends Account> findByName(String name, int limit, int offset);

    Account save(Account account);

    Account update(UUID id, Account account);

    void deleteById(UUID id);
}
