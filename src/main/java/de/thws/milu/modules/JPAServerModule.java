package de.thws.milu.modules;

import com.google.inject.AbstractModule;
import de.thws.milu.domain.repository.AccountRepository;
import de.thws.milu.domain.repository.BoardRepository;
import de.thws.milu.domain.repository.TodoRepository;
import de.thws.milu.infrastructure.persistence.jpa.JpaAccountRepository;
import de.thws.milu.infrastructure.persistence.jpa.JpaBoardRepository;
import de.thws.milu.infrastructure.persistence.jpa.JpaTodoRepository;
import jakarta.inject.Provider;
import jakarta.persistence.EntityManagerFactory;
import java.util.Objects;

public class JPAServerModule extends AbstractModule {

    private final Provider<EntityManagerFactory> entityManagerFactory;

    public JPAServerModule(Provider<EntityManagerFactory> entityManagerFactory) {
        this.entityManagerFactory = Objects.requireNonNull(entityManagerFactory);
    }

    @Override
    protected void configure() {
        bind(EntityManagerFactory.class).toProvider(entityManagerFactory);
        bind(AccountRepository.class).to(JpaAccountRepository.class);
        bind(BoardRepository.class).to(JpaBoardRepository.class);
        bind(TodoRepository.class).to(JpaTodoRepository.class);
    }
}
