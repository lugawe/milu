package de.thws.milu.modules;

import com.google.inject.AbstractModule;
import de.thws.milu.domain.repository.AccountRepository;
import de.thws.milu.domain.repository.BoardRepository;
import de.thws.milu.domain.repository.TodoRepository;
import de.thws.milu.infrastructure.persistence.JpaAccountRepository;
import de.thws.milu.infrastructure.persistence.JpaBoardRepository;
import de.thws.milu.infrastructure.persistence.JpaTodoRepository;

public class ServerModule extends AbstractModule {

    public ServerModule() {}

    @Override
    protected void configure() {
        bind(AccountRepository.class).to(JpaAccountRepository.class);
        bind(BoardRepository.class).to(JpaBoardRepository.class);
        bind(TodoRepository.class).to(JpaTodoRepository.class);
    }
}
