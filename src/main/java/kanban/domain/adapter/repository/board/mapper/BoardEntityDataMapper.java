package kanban.domain.adapter.repository.board.mapper;

import kanban.domain.adapter.repository.board.data.BoardData;
import kanban.domain.usecase.board.BoardEntity;

public class BoardEntityDataMapper {

    public static BoardData transformEntityToData(BoardEntity boardEntity) {
        BoardData boardData = new BoardData();

        boardData.setUserId(boardEntity.getUserId());
        boardData.setBoardId(boardEntity.getBoardId());
        boardData.setBoardName(boardEntity.getBoardName());
        boardData.setWorkflowIds(boardEntity.getWorkflowIds());

        return boardData;
    }
    public static BoardEntity transformDataToEntity(BoardData boardData) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setUserId(boardData.getUserId());
        boardEntity.setBoardId(boardData.getBoardId());
        boardEntity.setBoardName(boardData.getBoardName());
        boardEntity.setWorkflowIds(boardData.getWorkflowIds());

        return boardEntity;
    }
}
