package kanban.domain.model;

import java.util.UUID;

public class Card {

    private String name;
    private String cardId;
    private String stageId;

    public Card(String name, String stageId) {
        this.name = name;
        this.cardId = UUID.randomUUID().toString();
        this.stageId = stageId;
    }

    public String getCardId() {
        return cardId;
    }

    public String getCardName() {
        return name;
    }
}
