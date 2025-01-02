package de.thws.milu.adapter.in.resources;

import de.thws.milu.application.service.AccountService;
import de.thws.milu.core.domain.model.Account;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Singleton
@Path("/account")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    private final AccountService accountService;

    @Inject
    public AccountResource(AccountService accountService) {
        this.accountService = accountService;
    }

    @UnitOfWork
    @GET
    @Path("/{accountId}")
    public Response getById(@PathParam("accountId") UUID id) {

        Optional<Account> account = accountService.getById(id);
        if (account.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(account.get()).build();
    }

    @UnitOfWork
    @GET
    @Path("/")
    public Response getAll() {

        List<Account> accounts = accountService.getAll();

        return Response.ok(accounts).build();
    }

    @UnitOfWork
    @POST
    @Path("/")
    public Response save() {

        return Response.ok().build();
    }

    @UnitOfWork
    @DELETE
    @Path("/{accountId}")
    public Response deleteById(@PathParam("accountId") UUID id) {

        accountService.deleteById(id);

        return Response.ok().build();
    }
}
