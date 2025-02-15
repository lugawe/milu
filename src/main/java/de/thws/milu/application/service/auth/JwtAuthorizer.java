package de.thws.milu.application.service.auth;

import de.thws.milu.core.domain.model.Account;
import io.dropwizard.auth.Authorizer;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.jspecify.annotations.Nullable;

public class JwtAuthorizer implements Authorizer<Account> {

    @Inject
    public JwtAuthorizer() {}

    @Override
    public boolean authorize(Account principal, String role, @Nullable ContainerRequestContext requestContext) {
        return true;
    }
}
