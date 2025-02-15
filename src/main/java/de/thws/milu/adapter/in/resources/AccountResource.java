package de.thws.milu.adapter.in.resources;

import de.thws.milu.adapter.in.json.JsonAccount;
import de.thws.milu.application.service.AccountService;
import de.thws.milu.core.domain.model.Account;
import de.thws.milu.util.Resource;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

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
    public Response getById(@PathParam("accountId") UUID id, @Context UriInfo uriInfo) {

        Optional<Account> account = accountService.getById(id);
        if (account.isEmpty()) {
            return Response.noContent().build();
        }

        Account a = account.get();
        Resource<Account> resource = new Resource<>(a);
        String selfUri = uriInfo.getAbsolutePath().toString();
        resource.addLink("self", selfUri);
        resource.addLink("update", selfUri);
        resource.addLink("delete", selfUri);

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(3600);

        return Response.ok(resource).build();
    }

    @UnitOfWork
    @GET
    @Path("/")
    public Response getAll(
            @QueryParam("name") String name,
            @QueryParam("limit") @DefaultValue("10") int limit,
            @QueryParam("offset") @DefaultValue("0") int offset,
            @Context UriInfo uriInfo
    ) {
        List<Account> accounts = accountService.getAll(name, limit, offset);
        List<Resource<Account>> resources = accounts.stream().map(account -> {
            Resource<Account> res = new Resource<>(account);
            String selfUri = uriInfo.getBaseUriBuilder()
                    .path(AccountResource.class)
                    .path(account.getId().toString())
                    .build().toString();
            res.addLink("self", selfUri);
            res.addLink("update", selfUri);
            res.addLink("delete", selfUri);
            return res;
        }).toList();

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(3600);

        return Response.ok(resources).build();
    }

    @UnitOfWork
    @POST
    @Path("/")
    public Response save(JsonAccount account, @Context UriInfo uriInfo) {
        if (account == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Account savedAccount = accountService.save(account);

        Resource<Account> resource = new Resource<>(savedAccount);
        String selfUri = uriInfo.getAbsolutePathBuilder().path(savedAccount.getId().toString()).build().toString();
        resource.addLink("self", selfUri);

        return Response.ok(resource).build();
    }

    @UnitOfWork
    @PUT
    @Path("/{accountId}")
    public Response update(@PathParam("accountId") UUID id, JsonAccount account, @Context UriInfo uriInfo) {
        Optional<Account> existingAccount = accountService.getById(id);
        if (existingAccount.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Account not found")
                    .build();
        }
        Account updatedAccount = accountService.update(id, account);

        Resource<Account> resource = new Resource<>(updatedAccount);
        String selfUri = uriInfo.getAbsolutePath().toString();
        resource.addLink("self", selfUri);

        return Response.ok(resource).build();
    }

    @UnitOfWork
    @DELETE
    @Path("/{accountId}")
    public Response deleteById(@PathParam("accountId") UUID id) {

        accountService.deleteById(id);

        return Response.ok().build();
    }
}
