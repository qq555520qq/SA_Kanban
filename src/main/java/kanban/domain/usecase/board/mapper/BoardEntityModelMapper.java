package kanban.domain.usecase.board.mapper;

import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.board.BoardEntity;

public class BoardEntityModelMapper {

    public static Board transformEntityToModel(BoardEntity boardEntity) {
        Board board = new Board();

        board.setUserId(boardEntity.getUserId());
        board.setBoardId(boardEntity.getBoardId());
        board.setBoardName(boardEntity.getBoardName());
        board.setWorkflowIds(boardEntity.getWorkflowIds());

        return board;
    }
    public static BoardEntity transformModelToEntity(Board board) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setUserId(board.getUserId());
        boardEntity.setBoardId(board.getBoardId());
        boardEntity.setBoardName(board.getBoardName());
        boardEntity.setWorkflowIds(board.getWorkflowIds());

        return boardEntity;
    }
}
