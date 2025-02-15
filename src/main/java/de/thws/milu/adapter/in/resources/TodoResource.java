package de.thws.milu.adapter.in.resources;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaTodo;
import de.thws.milu.application.service.TodoService;
import de.thws.milu.core.domain.model.Todo;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@PermitAll
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

        return Response.ok(todo.get()).build();
    }

    @UnitOfWork
    @GET
    @Path("/")
    public Response getAll() {

        List<Todo> todos = todoService.getAll();

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
    @DELETE
    @Path("/{todoId}")
    public Response deleteById(@PathParam("todoId") UUID id) {

        todoService.deleteById(id);

        return Response.ok().build();
    }
}
