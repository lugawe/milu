package de.thws.milu.infrastructure.persistence.jpa;

import de.thws.milu.domain.exception.NoValuesAffectedException;
import de.thws.milu.domain.model.Board;
import de.thws.milu.domain.repository.BoardRepository;
import de.thws.milu.infrastructure.persistence.jpa.entity.JpaBoard;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaBoardRepository extends JpaRepository implements BoardRepository {

    private static final Logger log = LoggerFactory.getLogger(JpaBoardRepository.class);

    @Inject
    public JpaBoardRepository(JpaFactory jpaFactory) {
        super(jpaFactory);
    }

    @Override
    public Optional<Board> getById(UUID id) {

        log.debug("getById: {}", id);

        EntityManager entityManager = getEntityManager();

        JpaBoard board = entityManager.find(JpaBoard.class, id);

        return Optional.ofNullable(board);
    }

    @Override
    public void save(Board board) {

        log.debug("save: {}", board);

        EntityManager entityManager = getEntityManager();
        entityManager.persist(board);
    }

    @Override
    public void deleteById(UUID id) {

        log.debug("deleteById: {}", id);

        EntityManager entityManager = getEntityManager();

        Query deleteQuery = entityManager.createQuery("delete from JpaBoard a where a.id = :id");
        deleteQuery.setParameter("id", id);

        int n = deleteQuery.executeUpdate();
        if (n == 0) {
            throw new NoValuesAffectedException(String.format("cannot deleteById %s", id));
        }
    }
}
