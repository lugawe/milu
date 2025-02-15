package de.thws.milu.core.port.in;

import de.thws.milu.core.domain.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountServicePort {
    Optional<Account> getById(UUID id);

    Optional<Account> getByNameAndPassword(String name, String plainPassword);

    List<? extends Account> getAll();

    void save(Account account);

    void deleteById(UUID id);
}
