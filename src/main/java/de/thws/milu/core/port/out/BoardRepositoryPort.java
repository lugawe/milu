package de.thws.milu.core.port.out;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaBoard;
import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.domain.model.Board;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardRepositoryPort {
    Optional<Board> getById(Account caller, UUID id);

    List<JpaBoard> getAll(Account caller, int limit, int offset);

    List<JpaBoard> findByName(Account caller, String name, int limit, int offset);

    Board save(Account caller, Board board);

    Board update(Account caller, UUID id, Board board);

    void deleteById(Account caller, UUID id);
}
