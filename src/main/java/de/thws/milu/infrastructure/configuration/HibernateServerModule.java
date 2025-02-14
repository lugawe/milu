package de.thws.milu.infrastructure.configuration;

import com.google.inject.AbstractModule;
import de.thws.milu.adapter.out.persistence.hibernate.HibernateJpaFactory;
import de.thws.milu.adapter.out.persistence.jpa.JpaAccountRepository;
import de.thws.milu.adapter.out.persistence.jpa.JpaBoardRepository;
import de.thws.milu.adapter.out.persistence.jpa.JpaFactory;
import de.thws.milu.adapter.out.persistence.jpa.JpaTodoRepository;
import de.thws.milu.core.port.out.AccountRepositoryPort;
import de.thws.milu.core.port.out.BoardRepositoryPort;
import de.thws.milu.core.port.out.TodoRepositoryPort;
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
        bind(AccountRepositoryPort.class).to(JpaAccountRepository.class);
        bind(BoardRepositoryPort.class).to(JpaBoardRepository.class);
        bind(TodoRepositoryPort.class).to(JpaTodoRepository.class);
    }
}
