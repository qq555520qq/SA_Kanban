package kanban.domain.adapter.rest.board.get;


import kanban.domain.ApplicationContext;
import kanban.domain.adapter.presenter.board.get.GetBoardsPresenter;
import kanban.domain.usecase.board.get.GetBoardsInput;
import kanban.domain.usecase.board.get.GetBoardsUseCase;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class GetBoardsAdapter {
    @Path("/{userId}/boards")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBoards(@PathParam("userId") String userId){

        GetBoardsUseCase getBoardsUseCase = ApplicationContext.getInstance().getGetBoardsUseCase();
        GetBoardsInput input = getBoardsUseCase;
        input.setUserId(userId);

        GetBoardsPresenter presenter = new GetBoardsPresenter();

        getBoardsUseCase.execute(input, presenter);

        return Response.status(Response.Status.OK).entity(presenter.build()).build();
    }
}
