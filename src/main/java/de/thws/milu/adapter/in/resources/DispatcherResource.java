package de.thws.milu.adapter.in.resources;

import de.thws.milu.util.Resource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DispatcherResource {

    @GET
    public Response getDispatcherState(@Context UriInfo uriInfo) {
        Resource<Void> resource = new Resource<>(null);

        String baseUri = uriInfo.getBaseUriBuilder().build().toString();
        resource.addLink("accounts", baseUri + "account");
        resource.addLink("boards", baseUri + "board");
        resource.addLink("todos", baseUri + "todo");

        resource.addLink("self", uriInfo.getAbsolutePath().toString());

        return Response.ok(resource).build();
    }
}