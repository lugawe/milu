package de.thws.milu.core.port.in;

import de.thws.milu.adapter.in.json.JsonAccount;
import de.thws.milu.core.domain.model.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountServicePort {
    Optional<Account> getById(UUID id);

    List<? extends Account> getAll(String name, int limit, int offset);
    Optional<Account> getByNameAndPassword(String name, String plainPassword);

    void save(Account account);

    void update(UUID id, JsonAccount account);

    void deleteById(UUID id);

    String createAccessToken(Account account);
}
