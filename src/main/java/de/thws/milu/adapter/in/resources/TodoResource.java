package de.thws.milu.adapter.in.resources;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaTodo;
import de.thws.milu.application.service.TodoService;
import de.thws.milu.core.domain.model.Todo;
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
@Path("/todo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    private final TodoService todoService;

    @Inject
    public TodoResource(TodoService todoService) {
        this.todoService = todoService;
    }

    @UnitOfWork
    @GET
    @Path("/{todoId}")
    public Response getById(@PathParam("todoId") UUID id, @Context UriInfo uriInfo) {

        Optional<Todo> todo = todoService.getById(id);
        if (todo.isEmpty()) {
            return Response.noContent().build();
        }

        Todo t = todo.get();
        Resource<Todo> resource = new Resource<>(t);
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
            @QueryParam("state") String state,
            @QueryParam("limit") @DefaultValue("10") int limit,
            @QueryParam("offset") @DefaultValue("0") int offset,
            @Context UriInfo uriInfo
    ) {
        List<Todo> todos = todoService.getAll(name, state, limit, offset);

        List<Resource<Todo>> resources = todos.stream().map(todo -> {
            Resource<Todo> res = new Resource<>(todo);
            String selfUri = uriInfo.getBaseUriBuilder()
                    .path(TodoResource.class)
                    .path(todo.getId().toString())
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
    public Response save(JpaTodo todo, @Context UriInfo uriInfo) {

        todoService.save(todo);

        Resource<Todo> resource = new Resource<>(todo);
        String selfUri = uriInfo.getAbsolutePathBuilder().path(todo.getId().toString()).build().toString();
        resource.addLink("self", selfUri);

        return Response.ok(resource).build();
    }

    @UnitOfWork
    @PUT
    @Path("/{todoId}")
    public Response update(@PathParam("todoId") UUID id, JpaTodo updatedTodo, @Context UriInfo uriInfo) {
        Optional<Todo> existingTodo = todoService.getById(id);

        if (existingTodo.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        todoService.update(id, updatedTodo);

        Resource<Todo> resource = new Resource<>(updatedTodo);
        String selfUri = uriInfo.getAbsolutePath().toString();
        resource.addLink("self", selfUri);

        return Response.ok(resource).build();
    }

    @UnitOfWork
    @DELETE
    @Path("/{todoId}")
    public Response deleteById(@PathParam("todoId") UUID id) {

        todoService.deleteById(id);

        return Response.ok().build();
    }
}
