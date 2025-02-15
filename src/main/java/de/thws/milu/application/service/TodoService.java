package de.thws.milu.application.service;

import com.google.inject.Inject;
import de.thws.milu.adapter.in.json.JsonTodo;
import de.thws.milu.adapter.out.persistence.jpa.JpaTodoRepository;
import de.thws.milu.adapter.out.persistence.jpa.entity.JpaTodo;
import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.domain.model.Todo;
import de.thws.milu.core.port.in.TodoServicePort;
import de.thws.milu.core.port.out.TodoRepositoryPort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TodoService implements TodoServicePort {

    private final TodoRepositoryPort todoRepository;

    @Inject
    public TodoService(TodoRepositoryPort todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Optional<Todo> getById(Account caller, UUID id) {
        return todoRepository.getById(id);
    }

    @Override
    public List<Todo> getAll(Account caller, String name, String state, int limit, int offset) {
        return todoRepository.findByNameAndState(name, state, limit, offset).stream()
                .map(a -> (Todo) a)
                .toList();
    }

    @Override
    public void save(Account caller, Todo todo) {
        todoRepository.save(todo);
    }

    @Override
    public void update(Account caller, UUID id, JsonTodo todo) {
        todoRepository.update(id, todo);
    }

    @Override
    public void deleteById(Account caller, UUID id) {
        todoRepository.deleteById(id);
    }
}
