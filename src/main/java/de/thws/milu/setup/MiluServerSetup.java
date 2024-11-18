package de.thws.milu.setup;

import com.google.inject.AbstractModule;
import de.thws.milu.MiluServerConfiguration;
import de.thws.milu.modules.HibernateServerModule;
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
