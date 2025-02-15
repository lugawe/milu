package de.thws.milu.adapter.out.persistence.jpa;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaAccount;
import de.thws.milu.adapter.out.persistence.jpa.entity.JpaBoard;
import de.thws.milu.adapter.out.persistence.jpa.entity.JpaTodo;
import de.thws.milu.core.domain.exception.NoValuesAffectedException;
import de.thws.milu.core.domain.model.Board;
import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.domain.model.Todo;
import de.thws.milu.core.port.out.TodoRepositoryPort;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaTodoRepository extends JpaRepository implements TodoRepositoryPort {

    private static final Logger log = LoggerFactory.getLogger(JpaTodoRepository.class);

    @Inject
    public JpaTodoRepository(JpaFactory jpaFactory) {
        super(jpaFactory);
    }

    @Override
    public Optional<Todo> getById(Account caller, UUID id) {

        log.debug("getById: {}", id);

        EntityManager entityManager = getEntityManager();
        JpaTodo todo = entityManager.find(JpaTodo.class, id);

        return Optional.ofNullable(todo);
    }

    @Override
    public List<? extends Todo> getAll(Account caller, int limit, int offset) {

        log.debug("getAll");

        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<JpaTodo> criteriaQuery = criteriaBuilder.createQuery(JpaTodo.class);
        Root<JpaTodo> root = criteriaQuery.from(JpaTodo.class);

        CriteriaQuery<JpaTodo> selectAll = criteriaQuery.select(root);

        return entityManager.createQuery(selectAll).getResultList();
    }

    @Override
    public List<? extends Todo> findByNameAndState(Account caller, String name, String state, int limit, int offset) {
        log.debug("findByNameAndState: {} {}", name, state);

        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<JpaTodo> query = cb.createQuery(JpaTodo.class);
        Root<JpaTodo> root = query.from(JpaTodo.class);

        Predicate predicate = cb.conjunction();

        if (name != null && !name.trim().isEmpty()) {
            predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (state != null && !state.trim().isEmpty()) {
            try {
                Todo.State enumState = Todo.State.valueOf(state.toUpperCase());
                predicate = cb.and(predicate, cb.equal(root.get("state"), enumState));
            } catch (IllegalArgumentException e) {
                return List.of();
            }
        }

        query.where(predicate);

        return entityManager
                .createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Todo save(Account caller, Todo todo) {

        log.debug("save: {}", todo);

        EntityManager entityManager = getEntityManager();

        JpaTodo jpaTodo = new JpaTodo(todo);

        JpaBoard board = entityManager.find(JpaBoard.class, todo.getParentBoard().getId());
        jpaTodo.setParentBoard(board);

        entityManager.persist(jpaTodo);

        return jpaTodo;
    }

    @Override
    public Todo update(Account caller, UUID id, Todo todo) {
        log.debug("update Todo: {}", id);
        EntityManager em = getEntityManager();
        JpaTodo existingTodo = em.find(JpaTodo.class, id);

        if (existingTodo == null) {
            throw new NoValuesAffectedException(String.format("Todo with id %s not found", id));
        }

        if (todo.getName() != null && !todo.getName().isEmpty()) {
            existingTodo.setName(todo.getName());
        }

        if (todo.getDescription() != null && !todo.getDescription().isEmpty()) {
            existingTodo.setDescription(todo.getDescription());
        }

        if (todo.getState() != null) {
            existingTodo.setState(todo.getState());
        }

        if (todo.getParentBoard() != null) {
            existingTodo.setParentBoard(JpaBoardRepository.toEntity(todo.getParentBoard()));
        }

        em.merge(existingTodo);

        return existingTodo;
    }

    @Override
    public void deleteById(Account caller, UUID id) {

        log.debug("deleteById: {}", id);

        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<JpaTodo> criteriaDelete = criteriaBuilder.createCriteriaDelete(JpaTodo.class);
        Root<JpaTodo> root = criteriaDelete.from(JpaTodo.class);

        CriteriaDelete<JpaTodo> deleteById = criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

        int n = entityManager.createQuery(deleteById).executeUpdate();
        if (n == 0) {
            throw new NoValuesAffectedException(String.format("cannot deleteById %s", id));
        }
    }

    public static Board toDomain(JpaBoard jpaBoard) {

        return jpaBoard;
    }

    public static JpaTodo toEntity(Todo todo) {

        JpaTodo entity = new JpaTodo();
        entity.setId(todo.getId());
        entity.setName(todo.getName());
        entity.setDescription(todo.getDescription());
        entity.setState(todo.getState());
        entity.setParentBoard(JpaBoardRepository.toEntity(todo.getParentBoard()));


        return entity;
    }

    public static List<JpaTodo> toEntityList(List<? extends Todo> todos) {
        List<JpaTodo> entityList = new ArrayList<>();
        for (Todo t : todos) {
            entityList.add(toEntity(t));
        }

        return entityList;
    }
}
