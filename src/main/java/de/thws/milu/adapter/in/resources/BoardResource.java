package de.thws.milu.adapter.in.resources;

import de.thws.milu.adapter.in.json.JsonBoard;
import de.thws.milu.adapter.out.persistence.jpa.entity.JpaBoard;
import de.thws.milu.application.service.BoardService;
import de.thws.milu.core.domain.model.Account;
import de.thws.milu.core.domain.model.Board;
import de.thws.milu.util.Resource;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@PermitAll
@Singleton
@Path("/board")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BoardResource {

    private final BoardService boardService;

    @Inject
    public BoardResource(BoardService boardService) {
        this.boardService = boardService;
    }

    @UnitOfWork
    @GET
    @Path("/{boardId}")
    public Response getById(@Auth Account account, @PathParam("boardId") UUID id, @Context UriInfo uriInfo) {

        Optional<Board> board = boardService.getById(account, id);
        if (board.isEmpty()) {
            return Response.noContent().build();
        }

        Board b = board.get();
        Resource<Board> resource = new Resource<>(b);

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
            @Auth Account account,
            @QueryParam("name") String name,
            @QueryParam("limit") @DefaultValue("10") int limit,
            @QueryParam("offset") @DefaultValue("0") int offset,
            @Context UriInfo uriInfo) {

        List<Board> boards = boardService.getAll(account, name, limit, offset);
        List<Resource<Board>> resources = boards.stream()
                .map(board -> {
                    Resource<Board> res = new Resource<>(board);
                    String selfUri = uriInfo.getBaseUriBuilder()
                            .path(BoardResource.class)
                            .path(board.getId().toString())
                            .build()
                            .toString();
                    res.addLink("self", selfUri);
                    res.addLink("update", selfUri);
                    res.addLink("delete", selfUri);
                    return res;
                })
                .toList();

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(3600);

        return Response.ok(resources).build();
    }

    @UnitOfWork
    @POST
    @Path("/")
    public Response save(@Auth Account account,  JsonBoard board, @Context UriInfo uriInfo) {

        boardService.save(account, board);

        Resource<Board> resource = new Resource<>(board);
        String selfUri = uriInfo.getAbsolutePathBuilder()
                .path(board.getId().toString())
                .build()
                .toString();
        resource.addLink("self", selfUri);

        return Response.ok(resource).build();
    }

    @UnitOfWork
    @PUT
    @Path("/{boardId}")
    public Response update(
            @Auth Account account, @PathParam("boardId") UUID id, JsonBoard updatedBoard, @Context UriInfo uriInfo) {
        Optional<Board> existingBoard = boardService.getById(account, id);

        if (existingBoard.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        boardService.update(account, id, updatedBoard);

        Resource<Board> resource = new Resource<>(updatedBoard);
        String selfUri = uriInfo.getAbsolutePath().toString();
        resource.addLink("self", selfUri);

        return Response.ok(resource).build();
    }

    @UnitOfWork
    @DELETE
    @Path("/{boardId}")
    public Response deleteById(@Auth Account account, @PathParam("boardId") UUID id) {

        boardService.deleteById(account, id);

        return Response.ok().build();
    }
}
