package de.thws.milu.infrastructure.persistence.jpa;

import de.thws.milu.domain.exception.NoValuesAffectedException;
import de.thws.milu.domain.model.Account;
import de.thws.milu.domain.repository.AccountRepository;
import de.thws.milu.infrastructure.persistence.jpa.entity.JpaAccount;
import de.thws.milu.util.Casting;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JpaAccountRepository extends JpaRepository implements AccountRepository {

    private static final Logger log = LoggerFactory.getLogger(JpaAccountRepository.class);

    @Inject
    public JpaAccountRepository(JpaFactory jpaFactory) {
        super(jpaFactory);
    }

    @Override
    public Optional<Account> getById(UUID id) {

        log.debug("getById: {}", id);

        EntityManager entityManager = getEntityManager();

        JpaAccount account = entityManager.find(JpaAccount.class, id);

        return Optional.ofNullable(account);
    }

    @Override
    public List<? extends Account> getAll() {

        log.debug("getAll");

        EntityManager entityManager = getEntityManager();

        Query selectQuery = entityManager.createQuery("from JpaAccount");

        return Casting.cast(selectQuery.getResultList());
    }

    @Override
    public void save(Account account) {

        log.debug("save: {}", account);

        EntityManager entityManager = getEntityManager();
        entityManager.persist(account);
    }

    @Override
    public void deleteById(UUID id) {

        log.debug("deleteById: {}", id);

        EntityManager entityManager = getEntityManager();

        Query deleteQuery = entityManager.createQuery("delete from JpaAccount a where a.id = :id");
        deleteQuery.setParameter("id", id);

        int n = deleteQuery.executeUpdate();
        if (n == 0) {
            throw new NoValuesAffectedException(String.format("cannot deleteById %s", id));
        }
    }
}
