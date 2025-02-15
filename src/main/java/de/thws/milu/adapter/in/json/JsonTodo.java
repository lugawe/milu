package de.thws.milu.adapter.in.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.thws.milu.core.domain.model.Board;
import de.thws.milu.core.domain.model.Todo;

import java.util.Objects;
import java.util.UUID;

public class JsonTodo implements Todo {

    private UUID id;
    private String name;
    private String description;
    private State state;
    private Board parentBoard;

    public JsonTodo() {}

    public JsonTodo(Todo todo) {
        this.id = todo.getId();
        this.name = todo.getName();
        this.description = todo.getDescription();
        this.state = todo.getState();

        Board parentBoard = todo.getParentBoard();
        if (parentBoard != null) {
            this.parentBoard = new JsonBoard(parentBoard);
        }
    }

    public JsonTodo(UUID id, String name, String description, State state, Board parentBoard) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
        this.parentBoard = parentBoard;
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
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @JsonIgnore
    @Override
    public Board getParentBoard() {
        return parentBoard;
    }

    public void setParentBoard(Board parentBoard) {
        this.parentBoard = parentBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonTodo)) return false;
        JsonTodo jsonTodo = (JsonTodo) o;
        return Objects.equals(id, jsonTodo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
