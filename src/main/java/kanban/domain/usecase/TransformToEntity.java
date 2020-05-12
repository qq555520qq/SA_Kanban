package kanban.domain.usecase;

import kanban.domain.model.aggregate.board.Board;
import kanban.domain.usecase.entity.BoardEntity;

public class TransformToEntity {

    public static Board transform(BoardEntity boardEntity) {
        Board board = new Board();

        board.setBoardId(boardEntity.getBoardId());
        board.setBoardName(boardEntity.getBoardName());
        board.setWorkflowIds(boardEntity.getWorkflowIds());

        return board;
    }

}
