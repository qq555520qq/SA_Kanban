package kanban.domain.usecase.board.get;

import kanban.domain.usecase.board.BoardDTO;
import kanban.domain.usecase.board.BoardEntity;

import java.util.List;

public interface GetBoardsOutput {


    public List<BoardDTO> getBoardDTOs();

    public void setBoardDTOs(List<BoardDTO> boardDTOs);
}
