package kanban.domain.model.aggregate.board;

import kanban.domain.model.aggregate.AggregateRoot;
import kanban.domain.model.aggregate.board.event.BoardCreated;
import kanban.domain.model.aggregate.board.event.WorkflowCommitted;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board extends AggregateRoot {

    private String userId;
    private String boardName;
    private String boardId;
    private List<String> workflowIds;

    public Board() {
        this.workflowIds = new ArrayList<>();
    }

    public Board(String userId, String boardName) {
        this.userId = userId;
        this.boardName = boardName;
        this.boardId = UUID.randomUUID().toString();
        this.workflowIds = new ArrayList<>();
        addDomainEvent(new BoardCreated(userId, boardId, boardName));
    }

    public String commitWorkflow(String workflowId) {
        workflowIds.add(workflowId);
        addDomainEvent(new WorkflowCommitted(boardId, workflowId));
        return workflowId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
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

    public List<String> getWorkflowIds() {
        return workflowIds;
    }

    public void setWorkflowIds(List<String> workflowIds) {
        this.workflowIds = workflowIds;
    }
}
