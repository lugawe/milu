package de.thws.milu.adapter.in.json;

import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.domain.model.Board;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class JsonAccount implements Account {

    private UUID id;
    private String name;
    private String password;
    private List<? extends Board> boards;

    public JsonAccount(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.password = account.getPassword();
        this.boards = account.getBoards();
    }

    public JsonAccount(UUID id, String name, String password, List<Board> boards) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.boards = boards;
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
    public List<? extends Board> getBoards() {
        return boards;
    }

    public void setBoards(List<? extends Board> boards) {
        this.boards = boards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonAccount)) return false;
        JsonAccount that = (JsonAccount) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
