package kanban.domain.usecase.card.repository;

import kanban.domain.model.aggregate.card.Card;

public interface ICardRepository {
    public void add(Card card);

    public Card getCardById(String cardId);

    public void save(Card card);
}
