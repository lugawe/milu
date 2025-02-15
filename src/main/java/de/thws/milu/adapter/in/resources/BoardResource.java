package de.thws.milu.adapter.in.resources;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaBoard;
import de.thws.milu.application.service.BoardService;
import de.thws.milu.core.domain.model.Board;
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
    public Response getById(@PathParam("boardId") UUID id) {

        Optional<Board> board = boardService.getById(id);
        if (board.isEmpty()) {
            return Response.noContent().build();
        }

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(3600);

        return Response.ok(board.get()).build();
    }

    @UnitOfWork
    @GET
    @Path("/")
    public Response getAll(
            @QueryParam("name") String name,
            @QueryParam("limit") @DefaultValue("10") int limit,
            @QueryParam("offset") @DefaultValue("0") int offset
    ) {

        List<Board> boards = boardService.getAll(name, limit, offset);


        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(3600);

        return Response.ok(boards).build();
    }

    @UnitOfWork
    @POST
    @Path("/")
    public Response save(JpaBoard board) {

        boardService.save(board);

        return Response.ok().build();
    }

    @UnitOfWork
    @PUT
    @Path("/{boardId}")
    public Response update(@PathParam("boardId") UUID id, JpaBoard updatedBoard) {
        Optional<Board> existingBoard = boardService.getById(id);

        if (existingBoard.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        boardService.update(id, updatedBoard);
        return Response.ok(updatedBoard).build();
    }

    @UnitOfWork
    @DELETE
    @Path("/{boardId}")
    public Response deleteById(@PathParam("boardId") UUID id) {

        boardService.deleteById(id);

        return Response.ok().build();
    }
}
