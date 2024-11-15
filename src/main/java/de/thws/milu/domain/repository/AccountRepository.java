package de.thws.milu.domain.repository;

import de.thws.milu.domain.model.Account;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

    Optional<Account> getById(UUID id);

    void save(Account account);

    void deleteById(UUID id);
}
