package de.thws.milu.adapter.in.resources;

import de.thws.milu.application.service.AccountService;
import de.thws.milu.core.domain.model.Account;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Optional;

@Singleton
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    private AccountService accountService;

    @UnitOfWork
    @Path("/token")
    @GET
    public Response generateAccessToken(@QueryParam("name") String name, @QueryParam("password") String password) {

        Optional<Account> accountOptional = accountService.getByNameAndPassword(name, password);
        if (accountOptional.isEmpty()) {
            throw new ForbiddenException();
        }

        String token = accountService.createAccessToken(accountOptional.get());

        return Response.ok(token).build();
    }
}
