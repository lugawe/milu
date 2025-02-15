package de.thws.milu.adapter.in.resources;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaAccount;
import de.thws.milu.application.service.AccountService;
import de.thws.milu.core.domain.model.Account;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.CacheControl;
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

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(3600);

        return Response.ok(account.get()).build();
    }

    @UnitOfWork
    @GET
    @Path("/")
    public Response getAll(
            @QueryParam("name") String name,
            @QueryParam("limit") @DefaultValue("10") int limit,
            @QueryParam("offset") @DefaultValue("0") int offset
    ) {
        // Retrieve accounts from service with filtering and pagination applied.
        List<Account> accounts = accountService.getAll(name, limit, offset);

        // Set CacheControl header (cache for 1 hour)
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(3600);

        return Response.ok(accounts).build();
    }

    @UnitOfWork
    @POST
    @Path("/")
    public Response save(JpaAccount account) {
        if (account == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        accountService.save(account);

        return Response.ok().build();
    }

    @UnitOfWork
    @PUT
    @Path("/{accountId}")
    public Response update(@PathParam("accountId") UUID id, JpaAccount updatedAccount) {
        Optional<Account> existingAccount = accountService.getById(id);
        if (existingAccount.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Account not found")
                    .build();
        }
        accountService.update(id, updatedAccount);
        return Response.ok(updatedAccount).build();
    }

    @UnitOfWork
    @DELETE
    @Path("/{accountId}")
    public Response deleteById(@PathParam("accountId") UUID id) {

        accountService.deleteById(id);

        return Response.ok().build();
    }
}
