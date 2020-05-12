package kanban.domain.model.aggregate.workflow.event;

import kanban.domain.model.DomainEvent;
import kanban.domain.model.common.DateProvider;

import java.util.Date;

public class CardCommitted implements DomainEvent {

    private Date occurredOn;
    private String stageId;
    private String cardId;


    public CardCommitted(String stageId, String cardId) {
        this.occurredOn = DateProvider.now();
        this.stageId = stageId;
        this.cardId = cardId;
    }

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    public String getStageId() {
        return stageId;
    }

    public String getCardId() {
        return cardId;
    }
}
