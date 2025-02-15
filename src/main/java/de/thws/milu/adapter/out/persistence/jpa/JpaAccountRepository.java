package de.thws.milu.adapter.out.persistence.jpa;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaAccount;
import de.thws.milu.core.domain.exception.NoValuesAffectedException;
import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.port.out.AccountRepositoryPort;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
    public Optional<Account> getByName(String name) {

        log.debug("getByNameAndPassword: {}", name);

        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("from JpaAccount where name = :name");
        query.setParameter("name", name);

        return Optional.ofNullable((JpaAccount) query.getSingleResult());
    }

    @Override
    public List<JpaAccount> findByName(String name, int limit, int offset) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<JpaAccount> cq = cb.createQuery(JpaAccount.class);
        Root<JpaAccount> root = cq.from(JpaAccount.class);

        // Case-insensitive filter on "name" field
        cq.select(root)
                .where(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));

        return em.createQuery(cq).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public List<? extends Account> getAll(int limit, int offset) {

        log.debug("getAll");

        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<JpaAccount> criteriaQuery = criteriaBuilder.createQuery(JpaAccount.class);
        Root<JpaAccount> root = criteriaQuery.from(JpaAccount.class);

        CriteriaQuery<JpaAccount> selectAll = criteriaQuery.select(root);

        return entityManager.createQuery(selectAll).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    @Override
    public Account save(Account account) {

        log.debug("save: {}", account);

        EntityManager entityManager = getEntityManager();

        JpaAccount jpaAccount = new JpaAccount(account);
        entityManager.persist(jpaAccount);

        return jpaAccount;
    }

    @Override
    public Account update(UUID id, Account account) {
        EntityManager entityManager = getEntityManager();

        JpaAccount existingAccount = entityManager.find(JpaAccount.class, id);
        if (existingAccount == null) {
            throw new NoValuesAffectedException(String.format("Account with id %s not found", id));
        }

        if (account.getName() != null && !account.getName().isEmpty()) {
            existingAccount.setName(account.getName());
        }

        if (account.getBoards() != null) {
            existingAccount.setBoards(JpaBoardRepository.toEntityList(account.getBoards()));
        }

        entityManager.merge(existingAccount);

        return existingAccount;
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
