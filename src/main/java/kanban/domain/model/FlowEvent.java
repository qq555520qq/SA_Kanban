package kanban.domain.model;

import kanban.domain.model.common.DateProvider;

import java.util.Date;

public class FlowEvent implements DomainEvent {
    private Date occurredOn;

    private String workflowId;
    private String stageId;
    private String cardId;

    public FlowEvent(String workflowId, String stageId, String cardId) {
        this.workflowId = workflowId;
        this.stageId = stageId;
        this.cardId = cardId;
        this.occurredOn = DateProvider.now();
    }

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getStageId() {
        return stageId;
    }

    public String getCardId() {
        return cardId;
    }
}
