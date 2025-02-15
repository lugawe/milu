package de.thws.milu.application.service;

import com.google.inject.Inject;
import de.thws.milu.adapter.out.persistence.jpa.entity.JpaBoard;
import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.domain.model.Board;
import de.thws.milu.core.port.in.BoardServicePort;
import de.thws.milu.core.port.out.BoardRepositoryPort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BoardService implements BoardServicePort {

    private final BoardRepositoryPort boardRepository;

    @Inject
    public BoardService(BoardRepositoryPort boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public Optional<Board> getById(Account caller, UUID id) {
        return boardRepository.getById(caller, id);
    }

    @Override
    public List<Board> getAll(Account caller, String name, int limit, int offset) {
        List<JpaBoard> boards;

        if (name != null && !name.isEmpty()) {
            boards = boardRepository.findByName(caller, name, limit, offset);
        } else {
            boards = boardRepository.getAll(caller, limit, offset);
        }

        return boards.stream().map(b -> (Board) b).toList();
    }

    @Override
    public void save(Account caller, Board board) {
        boardRepository.save(caller, board);
    }

    @Override
    public void update(Account caller, UUID id, JpaBoard board) {
        boardRepository.update(caller, id, board);
    }

    @Override
    public void deleteById(Account caller, UUID id) {
        boardRepository.deleteById(caller, id);
    }
}
