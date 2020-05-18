package kanban.domain.usecase.board;

import java.util.List;

public class BoardEntity {
    private String userId;
    private String boardName;
    private String boardId;
    private List<String> workflowIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public List<String> getWorkflowIds() {
        return workflowIds;
    }

    public void setWorkflowIds(List<String> workflowIds) {
        this.workflowIds = workflowIds;
    }
}
