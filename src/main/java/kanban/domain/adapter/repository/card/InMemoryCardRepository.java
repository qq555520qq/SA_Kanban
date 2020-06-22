package kanban.domain.adapter.repository.card;

import kanban.domain.usecase.card.CardEntity;
import kanban.domain.usecase.card.ICardRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCardRepository implements ICardRepository {

    private List<CardEntity> cards;

    public InMemoryCardRepository() {
        cards = new ArrayList<CardEntity>();
    }

    @Override
    public void add(CardEntity cardEntity) {
        cards.add(cardEntity);
    }

    @Override
    public CardEntity getCardById(String cardId) {
        for (CardEntity each : cards) {
            if (each.getCardId().equalsIgnoreCase(cardId)) {
                return each;
            }
        }
        throw new RuntimeException("Card is not found,id=" + cardId);
    }


    @Override
    public void save(CardEntity cardEntity) {
        for (CardEntity each : cards) {
            if (each.getCardId().equalsIgnoreCase(cardEntity.getCardId())) {
                cards.set(cards.indexOf(each), cardEntity);
                break;
            }
        }
    }


}
