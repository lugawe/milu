package de.thws.milu.adapter.in.resources;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaTodo;
import de.thws.milu.application.service.TodoService;
import de.thws.milu.core.domain.model.Todo;
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
    public Response getById(@PathParam("todoId") UUID id) {

        Optional<Todo> todo = todoService.getById(id);
        if (todo.isEmpty()) {
            return Response.noContent().build();
        }

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(3600);

        return Response.ok(todo.get()).build();
    }

    @UnitOfWork
    @GET
    @Path("/")
    public Response getAll(
            @QueryParam("name") String name,
            @QueryParam("state") String state,
            @QueryParam("limit") @DefaultValue("10") int limit,
            @QueryParam("offset") @DefaultValue("0") int offset
    ) {
        // Retrieve todos with filtering and pagination applied.
        List<Todo> todos = todoService.getAll(name, state, limit, offset);

        // Set up cache control (cache for 1 hour)
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(3600);

        return Response.ok(todos).build();
    }

    @UnitOfWork
    @POST
    @Path("/")
    public Response save(JpaTodo todo) {

        todoService.save(todo);

        return Response.ok().build();
    }

    @UnitOfWork
    @PUT
    @Path("/{todoId}")
    public Response update(@PathParam("todoId") UUID id, JpaTodo updatedTodo) {
        Optional<Todo> existingTodo = todoService.getById(id);

        if (existingTodo.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        todoService.update(id, updatedTodo);
        return Response.ok(updatedTodo).build();
    }

    @UnitOfWork
    @DELETE
    @Path("/{todoId}")
    public Response deleteById(@PathParam("todoId") UUID id) {

        todoService.deleteById(id);

        return Response.ok().build();
    }
}
