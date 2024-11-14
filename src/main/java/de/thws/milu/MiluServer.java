package de.thws.milu;

import de.thws.milu.modules.ServerModule;
import de.thws.milu.util.Lazy;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class MiluServer extends Application<MiluConfiguration> {

    public static final String BASE_PACKAGE = "de.thws.milu";

    private static final Logger log = LoggerFactory.getLogger(MiluServer.class);

    private static final Lazy<MiluServer> instance = Lazy.of(MiluServer::new);

    public static void main(String[] args) throws Exception {
        instance.get().run(args);
    }

    @Override
    public void initialize(Bootstrap<MiluConfiguration> bootstrap) {
        bootstrap.addBundle(GuiceBundle.builder()
                .enableAutoConfig(BASE_PACKAGE)
                .modules(new ServerModule())
                .build());
    }

    @Override
    public void run(MiluConfiguration configuration, Environment environment) {
        log.info("Run MiluServer");
    }
}
