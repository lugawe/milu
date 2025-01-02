package de.thws.milu.adapter.out.persistence.jpa.entity;

import de.thws.milu.core.domain.model.Todo;
import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "todo")
public class JpaTodo implements Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @ManyToOne(optional = false)
    @JoinColumn(name = "parent_board")
    private JpaBoard parentBoard;

    public JpaTodo() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaTodo that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
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

    @Override
    public JpaBoard getParentBoard() {
        return parentBoard;
    }

    public void setParentBoard(JpaBoard parentBoard) {
        this.parentBoard = parentBoard;
    }
}
