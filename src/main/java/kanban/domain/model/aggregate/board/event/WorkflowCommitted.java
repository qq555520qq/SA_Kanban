package kanban.domain.model.aggregate.board.event;

import kanban.domain.model.DomainEvent;
import kanban.domain.model.common.DateProvider;

import java.util.Date;

public class WorkflowCommitted implements DomainEvent {

    private Date occurredOn;
    private String boardId;
    private String workflowId;

    public WorkflowCommitted(
            String boardId,
            String workflowId) {
        this.occurredOn = DateProvider.now();
        this.boardId = boardId;
        this.workflowId = workflowId;
    }

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    public String getBoardId() {
        return boardId;
    }

    public String getWorkflowId() {
        return workflowId;
    }
}
