package kanban.domain.model.aggregate.workflow.event;

import kanban.domain.model.FlowEvent;

public class CardCommitted extends FlowEvent {
    public CardCommitted(String workflowId, String stageId, String cardId) {
        super(workflowId, stageId, cardId);
    }
}
