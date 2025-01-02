package de.thws.milu.application.port.out;

import de.thws.milu.core.domain.model.Account;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

    Optional<Account> getById(UUID id);

    List<? extends Account> getAll();

    void save(Account account);

    void deleteById(UUID id);
}
