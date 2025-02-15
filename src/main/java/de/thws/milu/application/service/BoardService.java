package de.thws.milu.application.service;

import com.google.inject.Inject;
import de.thws.milu.adapter.out.persistence.jpa.entity.JpaBoard;
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
    public Optional<Board> getById(UUID id) {
        return boardRepository.getById(id);
    }

    @Override
    public List<Board> getAll(String name, int limit, int offset) {
        List<JpaBoard> boards;

        if (name != null && !name.isEmpty()) {
            boards = boardRepository.findByName(name, limit, offset);
        } else {
            boards = boardRepository.getAll(limit, offset);
        }

        return boards.stream().map(b -> (Board) b).toList();
    }

    @Override
    public void save(Board board) {
        boardRepository.save(board);
    }

    @Override
    public void update(UUID id, JpaBoard board) {
        boardRepository.update(id, board);
    }



    @Override
    public void deleteById(UUID id) {
        boardRepository.deleteById(id);
    }
}
