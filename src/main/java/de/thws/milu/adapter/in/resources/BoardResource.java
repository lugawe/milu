package de.thws.milu.adapter.in.resources;

import de.thws.milu.adapter.out.persistence.jpa.entity.JpaBoard;
import de.thws.milu.application.service.BoardService;
import de.thws.milu.core.domain.model.Board;
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

        return Response.ok(board.get()).build();
    }

    @UnitOfWork
    @GET
    @Path("/")
    public Response getAll() {

        List<Board> boards = boardService.getAll();

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
    @DELETE
    @Path("/{boardId}")
    public Response deleteById(@PathParam("boardId") UUID id) {

        boardService.deleteById(id);

        return Response.ok().build();
    }
}
