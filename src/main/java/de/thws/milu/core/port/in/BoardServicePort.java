package de.thws.milu.core.port.in;

import de.thws.milu.core.domain.model.Board;
import de.thws.milu.core.domain.model.Todo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardServicePort {
    Optional<Board> getById(UUID id);

    List<? extends Board> getAll();

    void save(Board board);

    void deleteById(UUID id);
}
