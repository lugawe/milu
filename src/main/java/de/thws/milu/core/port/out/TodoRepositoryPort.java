package de.thws.milu.core.port.out;

import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.domain.model.Todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoRepositoryPort {
    Optional<Todo> getById(UUID id);

    List<? extends Todo> getAll();

    void save(Todo todo);

    void deleteById(UUID id);
}
