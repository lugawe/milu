package de.thws.milu.application.service.auth;

import de.thws.milu.core.domain.model.Account;
import de.thws.milu.util.jwt.JwtHandler;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import jakarta.inject.Inject;
import java.util.Optional;

public class JwtAuthenticator implements Authenticator<String, Account> {

    private final JwtHandler jwtHandler;

    @Inject
    public JwtAuthenticator(JwtHandler jwtHandler) {
        this.jwtHandler = jwtHandler;
    }

    @Override
    public Optional<Account> authenticate(String token) throws AuthenticationException {

        return jwtHandler.decode(token, Account.class);
    }
}
