package kanban.domain.usecase;

import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.entity.BoardEntity;

public class TransformToDTO {

    public static BoardEntity transform(Board board) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setBoardId(board.getBoardId());
        boardEntity.setBoardName(board.getBoardName());
        boardEntity.setWorkflowIds(board.getWorkflowIds());

        return boardEntity;
    }
}
