package de.thws.milu.application.service;

import com.google.inject.Inject;
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
    public void save(Board board) {
        boardRepository.save(board);
    }

    @Override
    public void deleteById(UUID id) {
        boardRepository.deleteById(id);
    }
}
