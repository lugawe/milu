package de.thws.milu.core.port.out;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaTodo;
import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.domain.model.Todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoRepositoryPort {
    Optional<Todo> getById(UUID id);

    List<? extends Todo> getAll(int limit, int offset);
    List<? extends Todo> findByNameAndState(String name, String state, int limit, int offset);


    void save(Todo todo);

    void update(UUID id, JpaTodo todo);

    void deleteById(UUID id);
}
