package kanban.domain.adapter.presenter.board;

import kanban.domain.adapter.presenter.board.viewmodel.BoardViewModel;
import kanban.domain.usecase.board.BoardDTO;

public class BoardDTOViewModelMapper {
    public static BoardViewModel transformDTOToViewModel(BoardDTO boardDTO){
        BoardViewModel boardViewModel = new BoardViewModel();
        boardViewModel.setBoardId(boardDTO.getBoardId());
        boardViewModel.setBoardName(boardDTO.getBoardName());
        boardViewModel.setUserId(boardDTO.getUserId());
        boardViewModel.setWorkflowIds(boardDTO.getWorkflowIds());
        return boardViewModel;
    }
}
