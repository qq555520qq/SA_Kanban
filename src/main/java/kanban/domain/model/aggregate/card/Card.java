package kanban.domain.model.aggregate.card;

import kanban.domain.model.aggregate.AggregateRoot;
import kanban.domain.model.aggregate.card.event.CardCreated;

import java.util.UUID;

public class Card extends AggregateRoot {

    private String cardId;
    private String name;
    private String description;
    private String type;
    private String size;

    public Card() {}

    public Card(String workflowId, String stageId, String name, String description, String type, String size) {
        this.cardId = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.type = type;
        this.size = size;
        addDomainEvent(new CardCreated(workflowId, stageId, cardId, name, description, type, size));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
