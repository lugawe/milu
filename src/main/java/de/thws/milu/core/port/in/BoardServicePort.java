package de.thws.milu.core.port.in;

import de.thws.milu.core.domain.model.Board;

import java.util.Optional;
import java.util.UUID;

public interface BoardServicePort {
    Optional<Board> getById(UUID id);

    void save(Board board);

    void deleteById(UUID id);
}
