package de.thws.milu.infrastructure.configuration;

import com.google.inject.AbstractModule;
import io.dropwizard.core.ConfiguredBundle;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class MiluServerSetup extends AbstractModule implements ConfiguredBundle<MiluServerConfiguration> {

    private final MiluScanningHibernateBundle hibernateBundle = new MiluScanningHibernateBundle();

    public MiluServerSetup() {}

    @Override
    protected void configure() {
        install(new HibernateServerModule(hibernateBundle::getSessionFactory));
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        hibernateBundle.initialize(bootstrap);
    }

    @Override
    public void run(MiluServerConfiguration configuration, Environment environment) throws Exception {
        hibernateBundle.run(configuration, environment);
    }
}
