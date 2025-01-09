package de.thws.milu.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.thws.milu.util.Mapper;
import java.security.Principal;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtHandler {

    private static final Logger log = LoggerFactory.getLogger(JwtHandler.class);

    public static final String USER_CLAIM = "user";

    private final Algorithm algorithm;
    private final int lifetime;

    public JwtHandler(String secret, int lifetime) {
        this.algorithm = Algorithm.HMAC512(Objects.requireNonNull(secret, "secret"));
        this.lifetime = lifetime;
    }

    protected <T> Map<String, ?> map(T t) {
        return Mapper.toMap(t);
    }

    public <T extends Principal> String encode(T principal) {

        String id = UUID.randomUUID().toString();
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + 1000L * 60 * lifetime);
        Map<String, ?> mappedPrincipal = map(principal);

        String token = JWT.create()
                .withJWTId(id)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withClaim(USER_CLAIM, mappedPrincipal)
                .sign(algorithm);

        log.debug("encode: new jwt created {}", id);

        return token;
    }

    public <T extends Principal> Optional<T> decode(String token, Class<T> principalClass) {

        try {
            DecodedJWT jwt = JWT.require(algorithm)
                    .acceptExpiresAt(1)
                    .withClaimPresence(USER_CLAIM)
                    .build()
                    .verify(token);

            T principal = jwt.getClaim(USER_CLAIM).as(principalClass);

            log.debug("decode: jwt decoded");

            return Optional.of(principal);
        } catch (Exception e) {
            log.debug("decode: invalid jwt");
            return Optional.empty();
        }
    }
}
