package de.thws.milu.core.port.out;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaAccount;
import de.thws.milu.core.domain.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepositoryPort {
    Optional<Account> getById(UUID id);

    List<? extends Account> getAll(int limit, int offset);

    List<? extends Account> findByName(String name, int limit, int offset);

    void save(Account account);

    void update(UUID id, JpaAccount account);

    void deleteById(UUID id);
}
