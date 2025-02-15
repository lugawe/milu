package de.thws.milu.adapter.out.persistence.jpa.entity;

import de.thws.milu.core.domain.model.Account;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "account")
public class JpaAccount implements Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "account_id")
    private List<JpaBoard> boards;

    public JpaAccount() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JpaAccount that)) return false;
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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public List<JpaBoard> getBoards() {
        return boards;
    }

    public void setBoards(List<JpaBoard> boards) {
        this.boards = boards;
    }
}
