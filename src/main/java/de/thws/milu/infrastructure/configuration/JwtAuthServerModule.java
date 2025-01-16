package de.thws.milu.infrastructure.configuration;

import com.google.inject.AbstractModule;
import de.thws.milu.util.jwt.JwtHandler;
import java.time.Duration;

public class JwtAuthServerModule extends AbstractModule {

    private final JwtConfiguration jwtConfiguration;

    public JwtAuthServerModule(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    protected JwtHandler createJwtHandler() {
        return new JwtHandler(jwtConfiguration.getSecret(), Duration.ofSeconds(jwtConfiguration.getLifetime()));
    }

    @Override
    protected void configure() {
        bind(JwtHandler.class).toInstance(createJwtHandler());
    }
}
