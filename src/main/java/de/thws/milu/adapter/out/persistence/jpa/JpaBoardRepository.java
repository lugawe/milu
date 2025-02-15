package de.thws.milu.adapter.out.persistence.jpa;

import de.thws.milu.adapter.in.json.JsonBoard;
import de.thws.milu.adapter.out.persistence.jpa.entity.JpaBoard;
import de.thws.milu.core.domain.exception.NoValuesAffectedException;
import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.domain.model.Board;
import de.thws.milu.core.domain.model.Todo;
import de.thws.milu.core.port.out.BoardRepositoryPort;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaBoardRepository extends JpaRepository implements BoardRepositoryPort {

    private static final Logger log = LoggerFactory.getLogger(JpaBoardRepository.class);

    @Inject
    public JpaBoardRepository(JpaFactory jpaFactory) {
        super(jpaFactory);
    }

    @Override
    public Optional<Board> getById(Account caller, UUID id) {

        log.debug("getById: {}", id);

        EntityManager entityManager = getEntityManager();
        JpaBoard board = entityManager.find(JpaBoard.class, id);

        return Optional.ofNullable(board);
    }

    @Override
    public List<JpaBoard> findByName(Account caller, String name, int limit, int offset) {
        log.debug("findByNameContaining");

        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<JpaBoard> cq = cb.createQuery(JpaBoard.class);
        Root<JpaBoard> root = cq.from(JpaBoard.class);

        cq.select(root).where(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));

        return entityManager
                .createQuery(cq)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<JpaBoard> getAll(Account caller, int limit, int offset) {

        log.debug("getAll");

        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<JpaBoard> criteriaQuery = criteriaBuilder.createQuery(JpaBoard.class);
        Root<JpaBoard> root = criteriaQuery.from(JpaBoard.class);

        CriteriaQuery<JpaBoard> selectAll = criteriaQuery.select(root);

        return entityManager
                .createQuery(selectAll)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public void save(Account caller, Board board) {

        log.debug("save: {}", board);

        EntityManager entityManager = getEntityManager();
        entityManager.persist(board);
    }

    @Override
    public void update(Account caller, UUID id, Board board) {
        log.debug("update Board: id={}", id);
        EntityManager em = getEntityManager();
        JpaBoard existingBoard = em.find(JpaBoard.class, id);

        if (existingBoard == null) {
            throw new NoValuesAffectedException(String.format("Board with id %s not found", id));
        }

        // Update fields only if new values are provided.
        if (board.getName() != null && !board.getName().isEmpty()) {
            existingBoard.setName(board.getName());
        }

        if (board.getDescription() != null && !board.getDescription().isEmpty()) {
            existingBoard.setDescription(board.getDescription());
        }

        if (board.getTodos() != null) {
            existingBoard.setTodos(JpaTodoRepository.toEntityList(board.getTodos()));
        }

        em.merge(existingBoard);
    }

    @Override
    public void deleteById(Account caller, UUID id) {

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

    public static Board toDomain(JpaBoard jpaBoard) {

        return jpaBoard;
    }

    public static JpaBoard toEntity(Board board) {

        JpaBoard entity = new JpaBoard();
        entity.setId(board.getId());
        entity.setName(board.getName());
        entity.setDescription(board.getDescription());
        entity.setTodos(JpaTodoRepository.toEntityList(board.getTodos()));

        return entity;
    }

    public static List<JpaBoard> toEntityList(List<? extends Board> boards) {
        List<JpaBoard> entityList = new ArrayList<>();
        for (Board b : boards) {
            entityList.add(toEntity(b));
        }

        return entityList;
    }
}
