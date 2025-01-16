package de.thws.milu.infrastructure.configuration;

import io.dropwizard.core.ConfiguredBundle;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

public class MiluServerSetup extends DropwizardAwareModule<MiluServerConfiguration>
        implements ConfiguredBundle<MiluServerConfiguration> {

    private final MiluScanningHibernateBundle hibernateBundle = new MiluScanningHibernateBundle();

    public MiluServerSetup() {}

    @Override
    protected void configure() {
        install(new JwtAuthServerModule(configuration().getJwtConfiguration()));
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
