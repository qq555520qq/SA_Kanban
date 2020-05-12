package kanban.domain.model.aggregate.card.event;

import kanban.domain.model.common.DateProvider;
import kanban.domain.model.DomainEvent;

import java.util.Date;

public class CardCreated implements DomainEvent {

    private Date OccurredOn;
    private String workflowId;
    private String stageId;
    private String cardId;
    private String name;
    private String description;
    private String type;
    private String size;

    public CardCreated(String workflowId,
                       String stageId,
                       String cardId,
                       String name,
                       String description,
                       String type,
                       String size) {
        this.OccurredOn = DateProvider.now();
        this.workflowId = workflowId;
        this.stageId = stageId;
        this.cardId = cardId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.size = size;
    }

    @Override
    public Date getOccurredOn() {
        return OccurredOn;
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

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }
}
