package kanban.domain.model.aggregate.workflow.event;

import kanban.domain.model.FlowEvent;

public class CardUnCommitted extends FlowEvent {
    public CardUnCommitted(String workflowId, String stageId, String cardId) {
        super(workflowId, stageId, cardId);
    }
}
