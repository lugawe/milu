package de.thws.milu.application.service.auth;

import de.thws.milu.core.domain.model.Account;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.core.setup.Environment;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

@Provider
public class OAuthDynamicFeature extends AuthDynamicFeature {

    @Inject
    public OAuthDynamicFeature(JwtAuthenticator authenticator, JwtAuthorizer authorizer, Environment environment) {
        super(new OAuthCredentialAuthFilter.Builder<Account>()
                .setAuthenticator(authenticator)
                .setAuthorizer(authorizer)
                .setPrefix("Bearer")
                .buildAuthFilter());

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(Account.class));
    }
}
