package de.thws.milu.infrastructure.persistence;

import de.thws.milu.domain.model.Account;
import de.thws.milu.domain.repository.AccountRepository;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaAccountRepository extends JpaRepository implements AccountRepository {

    private static final Logger log = LoggerFactory.getLogger(JpaAccountRepository.class);

    @Inject
    public JpaAccountRepository(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public Optional<Account> getById(UUID id) {
        return Optional.empty();
    }

    @Override
    public void save(Account account) {}

    @Override
    public void deleteById(UUID id) {}
}