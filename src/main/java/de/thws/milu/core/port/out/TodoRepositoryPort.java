package de.thws.milu.core.port.out;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaTodo;
import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.domain.model.Todo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoRepositoryPort {
    Optional<Todo> getById(Account caller, UUID id);

    List<? extends Todo> getAll(Account caller, int limit, int offset);

    List<? extends Todo> findByNameAndState(Account caller, String name, String state, int limit, int offset);

    void save(Account caller, Todo todo);

    void update(Account caller, UUID id, Todo todo);

    void deleteById(Account caller, UUID id);
}
