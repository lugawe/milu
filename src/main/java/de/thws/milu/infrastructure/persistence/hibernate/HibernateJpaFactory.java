package de.thws.milu.infrastructure.persistence.hibernate;

import de.thws.milu.infrastructure.persistence.jpa.JpaFactory;
import jakarta.inject.Inject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateJpaFactory implements JpaFactory {

    private final SessionFactory sessionFactory;

    @Inject
    public HibernateJpaFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public SessionFactory getEntityManagerFactory() {
        return sessionFactory;
    }

    @Override
    public Session getEntityManager() {
        return sessionFactory.getCurrentSession();
    }
}
