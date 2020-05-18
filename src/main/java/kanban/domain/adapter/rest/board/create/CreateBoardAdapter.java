package kanban.domain.adapter.rest.board.create;

import kanban.domain.adapter.presenter.board.create.CreateBoardPresenter;
import kanban.domain.ApplicationContext;
import kanban.domain.usecase.board.create.CreateBoardInput;
import kanban.domain.usecase.board.create.CreateBoardUseCase;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class CreateBoardAdapter {
    @Path("/{userId}/boards")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBoard(@PathParam("userId") String userId, String boardNameJson){
        String boardName = "";
        try {
            JSONObject jsonObject = new JSONObject(boardNameJson);
            boardName = jsonObject.getString("name");
        }catch(JSONException e){
            e.printStackTrace();
        }

        CreateBoardUseCase createBoardUseCase = ApplicationContext.getInstance().getCreateBoardUseCase();
        CreateBoardInput input = createBoardUseCase;
        input.setUserId(userId);
        input.setBoardName(boardName);

        CreateBoardPresenter presenter = new CreateBoardPresenter();

        createBoardUseCase.execute(input, presenter);
        return Response.status(Response.Status.CREATED).entity(presenter.build()).build();
    }
}
