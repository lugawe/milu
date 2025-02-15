package de.thws.milu.core.port.in;

import de.thws.milu.adapter.in.json.JsonBoard;
import de.thws.milu.adapter.out.persistence.jpa.entity.JpaBoard;
import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.domain.model.Board;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardServicePort {
    Optional<Board> getById(Account caller, UUID id);

    List<? extends Board> getAll(Account caller, String name, int limit, int offset);

    void save(Account caller, Board board);

    void update(Account caller, UUID id, JsonBoard board);

    void deleteById(Account caller, UUID id);
}
