package de.thws.milu.adapter.out.persistence.jpa;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaAccount;
import de.thws.milu.core.domain.model.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public abstract class JpaRepository {

    private final JpaFactory jpaFactory;

    protected JpaRepository(JpaFactory jpaFactory) {
        this.jpaFactory = jpaFactory;
    }

    protected EntityManagerFactory getEntityManagerFactory() {
        return jpaFactory.getEntityManagerFactory();
    }

    protected EntityManager getEntityManager() {
        return jpaFactory.getEntityManager();
    }

    protected JpaAccount getJpaAccount(Account caller) {
        if (caller instanceof JpaAccount) {
            return getEntityManager().find(JpaAccount.class, caller.getId());
        }
        throw new RuntimeException();
    }
}
