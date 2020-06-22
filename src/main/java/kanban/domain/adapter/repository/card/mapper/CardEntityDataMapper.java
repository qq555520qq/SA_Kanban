package kanban.domain.adapter.repository.card.mapper;

import kanban.domain.adapter.repository.card.data.CardData;
import kanban.domain.model.aggregate.card.Card;
import kanban.domain.usecase.card.CardEntity;

public class CardEntityDataMapper {
    public static CardData transformEntityToData(CardEntity cardEntity) {
        CardData data = new CardData();

        data.setCardId(cardEntity.getCardId());
        data.setName(cardEntity.getName());
        data.setDescription(cardEntity.getDescription());
        data.setType(cardEntity.getType());
        data.setSize(cardEntity.getSize());
        return data;
    }

    public static CardEntity transformDataToEntity(CardData cardData) {
        CardEntity cardEntity = new CardEntity();

        cardEntity.setCardId(cardData.getCardId());
        cardEntity.setName(cardData.getName());
        cardEntity.setDescription(cardData.getDescription());
        cardEntity.setType(cardData.getType());
        cardEntity.setSize(cardData.getSize());
        return cardEntity;
    }
}
