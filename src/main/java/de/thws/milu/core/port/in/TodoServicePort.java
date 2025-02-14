package de.thws.milu.core.port.in;

import de.thws.milu.core.domain.model.Todo;

import java.util.Optional;
import java.util.UUID;

public interface TodoServicePort {
    Optional<Todo> getById(UUID id);

    void save(Todo todo);

    void deleteById(UUID id);
}
