package de.thws.milu.adapter.out.persistence.jpa;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaAccount;
import de.thws.milu.core.domain.exception.NoValuesAffectedException;
import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.port.out.AccountRepositoryPort;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JpaAccountRepository extends JpaRepository implements AccountRepositoryPort {

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
        Root<JpaAccount> root = criteriaQuery.from(JpaAccount.class);

        CriteriaQuery<JpaAccount> selectAll = criteriaQuery.select(root);

        return entityManager.createQuery(selectAll).getResultList();
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

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<JpaAccount> criteriaDelete = criteriaBuilder.createCriteriaDelete(JpaAccount.class);
        Root<JpaAccount> root = criteriaDelete.from(JpaAccount.class);

        CriteriaDelete<JpaAccount> deleteById = criteriaDelete.where(criteriaBuilder.equal(root.get("id"), id));

        int n = entityManager.createQuery(deleteById).executeUpdate();
        if (n == 0) {
            throw new NoValuesAffectedException(String.format("cannot deleteById %s", id));
        }
    }
}
