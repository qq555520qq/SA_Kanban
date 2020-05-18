package kanban.domain.adapter.presenter.board.create;

import kanban.domain.adapter.presenter.board.viewmodel.CreateBoardViewModel;
import kanban.domain.usecase.board.create.CreateBoardOutput;

public class CreateBoardPresenter implements CreateBoardOutput {
    private String boardId;

    public CreateBoardViewModel build(){
        CreateBoardViewModel viewModel = new CreateBoardViewModel();
        viewModel.setBoardId(boardId);
        return viewModel;
    }

    @Override
    public String getBoardId() {
        return boardId;
    }

    @Override
    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
