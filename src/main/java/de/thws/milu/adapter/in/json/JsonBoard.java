package de.thws.milu.adapter.in.json;

import de.thws.milu.core.domain.model.Board;
import de.thws.milu.core.domain.model.Todo;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class JsonBoard implements Board {

    private UUID id;
    private String name;
    private String description;
    private List<? extends Todo> todos;

    public JsonBoard(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();

        List<? extends Todo> todos = board.getTodos();
        if (todos != null) {
            this.todos = todos.stream().map(JsonTodo::new).toList();
        }
    }

    public JsonBoard(UUID id, String name, String description, List<Todo> todos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.todos = todos;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public List<? extends Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<? extends Todo> todos) {
        this.todos = todos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonBoard)) return false;
        JsonBoard that = (JsonBoard) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
