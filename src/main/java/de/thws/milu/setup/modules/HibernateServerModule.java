package de.thws.milu.setup.modules;

import com.google.inject.AbstractModule;
import de.thws.milu.domain.repository.AccountRepository;
import de.thws.milu.domain.repository.BoardRepository;
import de.thws.milu.domain.repository.TodoRepository;
import de.thws.milu.infrastructure.persistence.hibernate.HibernateJpaFactory;
import de.thws.milu.infrastructure.persistence.jpa.JpaAccountRepository;
import de.thws.milu.infrastructure.persistence.jpa.JpaBoardRepository;
import de.thws.milu.infrastructure.persistence.jpa.JpaFactory;
import de.thws.milu.infrastructure.persistence.jpa.JpaTodoRepository;
import jakarta.inject.Provider;
import org.hibernate.SessionFactory;

public class HibernateServerModule extends AbstractModule {

    private final Provider<SessionFactory> sessionFactoryProvider;

    public HibernateServerModule(Provider<SessionFactory> sessionFactoryProvider) {
        this.sessionFactoryProvider = sessionFactoryProvider;
    }

    @Override
    protected void configure() {
        bind(SessionFactory.class).toProvider(sessionFactoryProvider).asEagerSingleton();
        bind(JpaFactory.class).to(HibernateJpaFactory.class);
        bind(AccountRepository.class).to(JpaAccountRepository.class);
        bind(BoardRepository.class).to(JpaBoardRepository.class);
        bind(TodoRepository.class).to(JpaTodoRepository.class);
    }
}
