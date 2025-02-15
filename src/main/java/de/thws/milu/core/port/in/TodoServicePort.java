package de.thws.milu.core.port.in;

import de.thws.milu.adapter.in.json.JsonTodo;
import de.thws.milu.adapter.out.persistence.jpa.entity.JpaTodo;
import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.domain.model.Todo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoServicePort {
    Optional<Todo> getById(Account caller, UUID id);

    List<? extends Todo> getAll(Account caller, String name, String state, int limit, int offset);

    Todo save(Account caller, Todo todo);

    Todo update(Account caller, UUID id, JsonTodo todo);

    void deleteById(Account caller, UUID id);
}
