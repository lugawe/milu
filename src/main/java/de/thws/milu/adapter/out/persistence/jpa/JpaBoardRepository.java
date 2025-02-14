package de.thws.milu.adapter.out.persistence.jpa;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaBoard;
import de.thws.milu.core.domain.exception.NoValuesAffectedException;
import de.thws.milu.core.domain.model.Board;
import de.thws.milu.core.port.out.BoardRepositoryPort;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

public class JpaBoardRepository extends JpaRepository implements BoardRepositoryPort {

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

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<JpaBoard> criteriaDelete = criteriaBuilder.createCriteriaDelete(JpaBoard.class);
        Root<JpaBoard> root = criteriaDelete.from(JpaBoard.class);

        CriteriaDelete<JpaBoard> deleteById = criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

        int n = entityManager.createQuery(deleteById).executeUpdate();
        if (n == 0) {
            throw new NoValuesAffectedException(String.format("cannot deleteById %s", id));
        }
    }
}
