package kanban.domain.adapter.repository.card;

import kanban.domain.model.aggregate.card.Card;
import kanban.domain.usecase.card.repository.ICardRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCardRepository implements ICardRepository {

    private List<Card> cards;

    public InMemoryCardRepository() {
        cards = new ArrayList<Card>();
    }

    @Override
    public void add(Card card) {
        cards.add(card);
    }

    @Override
    public Card getCardById(String cardId) {
        for (Card each : cards) {
            if (each.getCardId().equalsIgnoreCase(cardId)) {
                return each;
            }
        }
        throw new RuntimeException("Card is not found,id=" + cardId);
    }


    @Override
    public void save(Card card) {
        for (Card each : cards) {
            if (each.getCardId().equalsIgnoreCase(card.getCardId())) {
                cards.set(cards.indexOf(each), card);
                break;
            }
        }
    }


}
