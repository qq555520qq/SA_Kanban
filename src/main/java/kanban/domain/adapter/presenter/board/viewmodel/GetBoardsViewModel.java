package kanban.domain.adapter.presenter.board.viewmodel;

import java.util.List;

public class GetBoardsViewModel {
    private List<BoardViewModel> boardViewModels;

    public List<BoardViewModel> getBoardViewModels() {
        return boardViewModels;
    }

    public void setBoardViewModels(List<BoardViewModel> boardViewModels) {
        this.boardViewModels = boardViewModels;
    }
}
