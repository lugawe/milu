package de.thws.milu.core.port.in;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaTodo;
import de.thws.milu.core.domain.model.Todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoServicePort {
    Optional<Todo> getById(UUID id);

    List<? extends Todo> getAll(String name, String state, int limit, int offset);

    void save(Todo todo);

    void update(UUID id, JpaTodo todo);

    void deleteById(UUID id);
}
