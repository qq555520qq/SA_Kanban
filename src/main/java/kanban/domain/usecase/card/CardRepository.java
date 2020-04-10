package kanban.domain.usecase.card;

import kanban.domain.model.Card;
import kanban.domain.model.Workflow;

import java.util.ArrayList;
import java.util.List;

public class CardRepository {

    private List<Card> cards;

    public CardRepository() {
        cards = new ArrayList<Card>();
    }

    public void add(Card card) {
        cards.add(card);
    }
}
