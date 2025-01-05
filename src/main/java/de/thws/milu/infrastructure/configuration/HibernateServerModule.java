package de.thws.milu.infrastructure.configuration;

import com.google.inject.AbstractModule;
import de.thws.milu.adapter.out.persistence.hibernate.HibernateJpaFactory;
import de.thws.milu.adapter.out.persistence.jpa.JpaAccountRepository;
import de.thws.milu.adapter.out.persistence.jpa.JpaBoardRepository;
import de.thws.milu.adapter.out.persistence.jpa.JpaFactory;
import de.thws.milu.adapter.out.persistence.jpa.JpaTodoRepository;
import de.thws.milu.application.port.out.AccountRepository;
import de.thws.milu.application.port.out.BoardRepository;
import de.thws.milu.application.port.out.TodoRepository;
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
