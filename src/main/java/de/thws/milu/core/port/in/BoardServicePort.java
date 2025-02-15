package de.thws.milu.core.port.in;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaBoard;
import de.thws.milu.core.domain.model.Board;
import de.thws.milu.core.domain.model.Todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardServicePort {
    Optional<Board> getById(UUID id);

    List<? extends Board> getAll(String name, int limit, int offset);

    void save(Board board);

    void update(UUID id, JpaBoard board);

    void deleteById(UUID id);
}
