package kanban.domain.usecase.board.mapper;

import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.board.BoardDTO;

public class BoardDTOModelMapper {
    public static BoardDTO transformModelToDTO(Board board){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardId(board.getBoardId());
        boardDTO.setBoardName(board.getBoardName());
        boardDTO.setUserId(board.getUserId());
        boardDTO.setWorkflowIds(board.getWorkflowIds());
        return boardDTO;
    }
}
