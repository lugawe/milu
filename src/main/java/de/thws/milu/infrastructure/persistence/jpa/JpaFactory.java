package de.thws.milu.infrastructure.persistence.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public interface JpaFactory {

    EntityManagerFactory getEntityManagerFactory();

    EntityManager getEntityManager();
}
