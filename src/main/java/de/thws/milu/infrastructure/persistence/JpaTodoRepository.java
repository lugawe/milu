package de.thws.milu.infrastructure.persistence;

import de.thws.milu.domain.model.Todo;
import de.thws.milu.domain.repository.TodoRepository;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaTodoRepository extends JpaRepository implements TodoRepository {

    private static final Logger log = LoggerFactory.getLogger(JpaTodoRepository.class);

    @Inject
    public JpaTodoRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public Optional<Todo> getById(UUID id) {
        return Optional.empty();
    }

    @Override
    public void save(Todo todo) {}

    @Override
    public void deleteById(UUID id) {}
}
