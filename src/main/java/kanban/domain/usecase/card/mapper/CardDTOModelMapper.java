package kanban.domain.usecase.card.mapper;

import kanban.domain.model.aggregate.card.Card;
import kanban.domain.usecase.card.CardDTO;

public class CardDTOModelMapper {
    public static Card transformDTOToModel(CardDTO cardDTO) {
        Card card = new Card();

        card.setCardId(cardDTO.getCardId());
        card.setName(cardDTO.getName());
        card.setDescription(cardDTO.getDescription());
        card.setType(cardDTO.getType());
        card.setSize(cardDTO.getSize());
        return card;
    }

    public static CardDTO transformModelToDTO(Card card) {
        CardDTO cardDTO = new CardDTO();

        cardDTO.setCardId(card.getCardId());
        cardDTO.setName(card.getName());
        cardDTO.setDescription(card.getDescription());
        cardDTO.setType(card.getType());
        cardDTO.setSize(card.getSize());
        return cardDTO;
    }
}
