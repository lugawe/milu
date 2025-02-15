package de.thws.milu.adapter.out.persistence.jpa.entity;

import de.thws.milu.core.domain.model.Board;
import de.thws.milu.core.domain.model.Todo;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "board")
public class JpaBoard implements Board {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private JpaAccount account;

    @OneToMany(mappedBy = "parentBoard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaTodo> todos;

    public JpaBoard(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();

        List<? extends Todo> todos = board.getTodos();
        if (todos != null) {
            this.todos = todos.stream().map(JpaTodo::new).toList();
        }
    }

    public JpaBoard() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaBoard that)) return false;
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

    public JpaAccount getAccount() {
        return account;
    }

    public void setAccount(JpaAccount account) {
        this.account = account;
    }

    @Override
    public List<JpaTodo> getTodos() {
        return todos;
    }

    public void setTodos(List<JpaTodo> todos) {
        this.todos = todos;
    }
}
