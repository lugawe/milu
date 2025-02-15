package de.thws.milu.infrastructure;

import de.thws.milu.infrastructure.configuration.MiluServerConfiguration;
import de.thws.milu.infrastructure.configuration.MiluServerSetup;
import de.thws.milu.util.Lazy;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class MiluServer extends Application<MiluServerConfiguration> {

    public static final String BASE_PACKAGE = "de.thws.milu";

    private static final Logger log = LoggerFactory.getLogger(MiluServer.class);

    private static final Lazy<MiluServer> instance = Lazy.of(MiluServer::new);

    public static void main(String[] args) throws Exception {
        instance.get().run(args);
    }

    @Override
    public void initialize(Bootstrap<MiluServerConfiguration> bootstrap) {

        MiluServerSetup setup = new MiluServerSetup(); // bundle and module

        bootstrap.addBundle(setup);
        bootstrap.addBundle(GuiceBundle.builder()
                .enableAutoConfig(BASE_PACKAGE)
                .modules(setup)
                .build());
    }

    @Override
    public void run(MiluServerConfiguration configuration, Environment environment) {
        log.info("Run MiluServer");

        environment.jersey().setUrlPattern("/api/*");
    }
}
