package kanban.domain.model.aggregate.workflow.event;

import kanban.domain.model.common.DateProvider;
import kanban.domain.model.DomainEvent;

import java.util.Date;

public class WorkflowCreated implements DomainEvent {

    private Date occurredOn;
    private String boardId;
    private String workflowId;
    private String name;

    public WorkflowCreated(String boardId, String workflowId, String name) {
        this.occurredOn = DateProvider.now();
        this.boardId = boardId;
        this.workflowId = workflowId;
        this.name = name;
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

    public String getName() {
        return name;
    }
}
