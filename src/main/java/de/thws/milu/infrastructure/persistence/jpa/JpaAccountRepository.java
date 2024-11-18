package de.thws.milu.infrastructure.persistence.jpa;

import de.thws.milu.domain.model.Account;
import de.thws.milu.domain.repository.AccountRepository;
import de.thws.milu.infrastructure.persistence.jpa.entity.JpaAccount;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<JpaAccount> criteriaQuery = criteriaBuilder.createQuery(JpaAccount.class);

        CriteriaQuery<JpaAccount> selectAll = criteriaQuery.select(criteriaQuery.from(JpaAccount.class));

        TypedQuery<JpaAccount> query = entityManager.createQuery(selectAll);

        return query.getResultList();
    }

    @Override
    public void save(Account account) {

        log.debug("save: {}", account);

        EntityManager entityManager = getEntityManager();
        entityManager.persist(account);
    }

    @Override
    public void deleteById(UUID id) {}
}
