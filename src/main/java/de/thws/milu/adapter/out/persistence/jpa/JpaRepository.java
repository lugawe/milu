package de.thws.milu.adapter.out.persistence.jpa;

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
}
