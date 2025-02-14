package de.thws.milu.core.port.out;

import de.thws.milu.core.domain.model.Board;

import java.util.Optional;
import java.util.UUID;

public interface BoardRepositoryPort {
    Optional<Board> getById(UUID id);

    void save(Board board);

    void deleteById(UUID id);
}
