package de.thws.milu.adapter.out.persistence.jpa;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaTodo;
import de.thws.milu.application.port.out.TodoRepository;
import de.thws.milu.core.domain.exception.NoValuesAffectedException;
import de.thws.milu.core.domain.model.Todo;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;
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

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<JpaTodo> criteriaDelete = criteriaBuilder.createCriteriaDelete(JpaTodo.class);
        Root<JpaTodo> root = criteriaDelete.from(JpaTodo.class);

        CriteriaDelete<JpaTodo> deleteById = criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

        int n = entityManager.createQuery(deleteById).executeUpdate();
        if (n == 0) {
            throw new NoValuesAffectedException(String.format("cannot deleteById %s", id));
        }
    }
}
