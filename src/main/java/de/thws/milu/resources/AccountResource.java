package de.thws.milu.resources;

import de.thws.milu.application.service.AccountService;
import de.thws.milu.domain.model.Account;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

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

    @GET
    public Response getAccounts() {

        List<Account> accounts = accountService.getAccounts();

        return Response.ok(accounts).build();
    }
}
