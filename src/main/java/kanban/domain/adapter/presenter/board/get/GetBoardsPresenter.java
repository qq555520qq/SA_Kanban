package kanban.domain.adapter.presenter.board.get;

import kanban.domain.adapter.presenter.board.BoardDTOViewModelMapper;
import kanban.domain.adapter.presenter.board.viewmodel.BoardViewModel;
import kanban.domain.adapter.presenter.board.viewmodel.GetBoardsViewModel;
import kanban.domain.usecase.board.BoardDTO;
import kanban.domain.usecase.board.get.GetBoardsOutput;

import java.util.ArrayList;
import java.util.List;

public class GetBoardsPresenter implements GetBoardsOutput {
    private List<BoardDTO> boardDTOs;
    public GetBoardsViewModel build(){
        List<BoardViewModel> boardViewModels = new ArrayList<>();
        for(BoardDTO boardDTO: boardDTOs){
            boardViewModels.add(BoardDTOViewModelMapper.transformDTOToViewModel(boardDTO));
        }

        GetBoardsViewModel getBoardsViewModel = new GetBoardsViewModel();
        getBoardsViewModel.setBoardViewModels(boardViewModels);
        return getBoardsViewModel;
    }


    @Override
    public List<BoardDTO> getBoardDTOs() {
        return boardDTOs;
    }

    @Override
    public void setBoardDTOs(List<BoardDTO> boardDTOs) {
        this.boardDTOs = boardDTOs;
    }
}
