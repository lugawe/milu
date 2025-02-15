package de.thws.milu.core.port.out;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaBoard;
import de.thws.milu.core.domain.model.Board;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardRepositoryPort {
    Optional<Board> getById(UUID id);

    List<JpaBoard> getAll(int limit, int offset);

    List<JpaBoard> findByName(String name, int limit, int offset);

    void save(Board board);

    void update(UUID id, Board board);

    void deleteById(UUID id);
}
