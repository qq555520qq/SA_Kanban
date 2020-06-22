package kanban.domain.usecase.card.mapper;

import kanban.domain.model.aggregate.card.Card;
import kanban.domain.usecase.card.CardEntity;

public class CardEntityModelMapper {

    public static Card transformEntityToModel(CardEntity cardEntity) {
        Card card = new Card();

        card.setCardId(cardEntity.getCardId());
        card.setName(cardEntity.getName());
        card.setDescription(cardEntity.getDescription());
        card.setType(cardEntity.getType());
        card.setSize(cardEntity.getSize());
        return card;
    }

    public static CardEntity transformModelToEntity(Card card) {
        CardEntity cardEntity = new CardEntity();

        cardEntity.setCardId(card.getCardId());
        cardEntity.setName(card.getName());
        cardEntity.setDescription(card.getDescription());
        cardEntity.setType(card.getType());
        cardEntity.setSize(card.getSize());
        return cardEntity;
    }
}
