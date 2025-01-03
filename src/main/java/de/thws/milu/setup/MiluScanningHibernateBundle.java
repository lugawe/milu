package de.thws.milu.setup;

import de.thws.milu.MiluServerConfiguration;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.ScanningHibernateBundle;

public class MiluScanningHibernateBundle extends ScanningHibernateBundle<MiluServerConfiguration> {

    public static final String ENTITY_PACKAGE = "de.thws.milu.infrastructure.persistence.jpa.entity";

    public MiluScanningHibernateBundle() {
        super(ENTITY_PACKAGE);
    }

    @Override
    public PooledDataSourceFactory getDataSourceFactory(MiluServerConfiguration configuration) {
        return configuration.getDataSourceFactory();
    }
}
