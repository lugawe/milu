package de.thws.milu.core.port.in;

import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.domain.model.Board;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BoardServicePort {
    Optional<Board> getById(Account caller, UUID id);

    List<? extends Board> getAll(Account caller, String name, int limit, int offset);

    Board save(Account caller, Board board);

    Board update(Account caller, UUID id, Board board);

    void deleteById(Account caller, UUID id);
}
