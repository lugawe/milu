package de.thws.milu.infrastructure.persistence.jpa;

import de.thws.milu.domain.model.Board;
import de.thws.milu.domain.repository.BoardRepository;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaBoardRepository extends JpaRepository implements BoardRepository {

    private static final Logger log = LoggerFactory.getLogger(JpaBoardRepository.class);

    @Inject
    public JpaBoardRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public Optional<Board> getById(UUID id) {
        return Optional.empty();
    }

    @Override
    public void save(Board board) {}

    @Override
    public void deleteById(UUID id) {}
}
