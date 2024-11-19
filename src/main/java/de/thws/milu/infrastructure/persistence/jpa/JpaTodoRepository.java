package de.thws.milu.infrastructure.persistence.jpa;

import de.thws.milu.domain.exception.NoValuesAffectedException;
import de.thws.milu.domain.model.Todo;
import de.thws.milu.domain.repository.TodoRepository;
import de.thws.milu.infrastructure.persistence.jpa.entity.JpaTodo;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaTodoRepository extends JpaRepository implements TodoRepository {

    private static final Logger log = LoggerFactory.getLogger(JpaTodoRepository.class);

    @Inject
    public JpaTodoRepository(JpaFactory jpaFactory) {
        super(jpaFactory);
    }

    @Override
    public Optional<Todo> getById(UUID id) {

        log.debug("getById: {}", id);

        EntityManager entityManager = getEntityManager();

        JpaTodo todo = entityManager.find(JpaTodo.class, id);

        return Optional.ofNullable(todo);
    }

    @Override
    public void save(Todo todo) {

        log.debug("save: {}", todo);

        EntityManager entityManager = getEntityManager();
        entityManager.persist(todo);
    }

    @Override
    public void deleteById(UUID id) {

        log.debug("deleteById: {}", id);

        EntityManager entityManager = getEntityManager();

        Query deleteQuery = entityManager.createQuery("delete from JpaTodo a where a.id = :id");
        deleteQuery.setParameter("id", id);

        int n = deleteQuery.executeUpdate();
        if (n == 0) {
            throw new NoValuesAffectedException(String.format("cannot deleteById %s", id));
        }
    }
}
