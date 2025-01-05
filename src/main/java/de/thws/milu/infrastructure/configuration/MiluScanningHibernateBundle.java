package de.thws.milu.infrastructure.configuration;

import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.ScanningHibernateBundle;

public class MiluScanningHibernateBundle extends ScanningHibernateBundle<MiluServerConfiguration> {

    public static final String ENTITY_PACKAGE = "de.thws.milu.adapter.out.persistence.jpa.entity";

    public MiluScanningHibernateBundle() {
        super(ENTITY_PACKAGE);
    }

    @Override
    public PooledDataSourceFactory getDataSourceFactory(MiluServerConfiguration configuration) {
        return configuration.getDataSourceFactory();
    }
}
