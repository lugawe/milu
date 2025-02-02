package de.thws.milu.application.port.out;

import de.thws.milu.core.domain.model.Board;
import java.util.Optional;
import java.util.UUID;

public interface BoardRepository {

    Optional<Board> getById(UUID id);

    void save(Board board);

    void deleteById(UUID id);
}
