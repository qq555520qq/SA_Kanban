package kanban.domain.unit_test;

import kanban.domain.model.aggregate.card.Card;
import kanban.domain.model.aggregate.card.event.CardCreated;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CardTest {
    @Test
    public void Create_card_should_have_CardCreated_event_in_domainEvent_list(){
        Card card = new Card("workflowId", "stageId", "cardName", "description", "CardType", "1");

        assertEquals(1, card.getDomainEvents().size());
        assertEquals(CardCreated.class, card.getDomainEvents().get(0).getClass());
    }
}
