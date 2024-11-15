package de.thws.milu.domain.repository;

import de.thws.milu.domain.model.Todo;
import java.util.Optional;
import java.util.UUID;

public interface TodoRepository {

    Optional<Todo> getById(UUID id);

    void save(Todo todo);

    void deleteById(UUID id);
}
