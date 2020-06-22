package kanban.domain.usecase.card.create;

import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.card.Card;
import kanban.domain.usecase.card.mapper.CardEntityModelMapper;
import kanban.domain.usecase.card.ICardRepository;

public class CreateCardUseCase {

    private ICardRepository cardRepository;
    private DomainEventBus eventBus;

    public CreateCardUseCase(ICardRepository cardRepository,
                             DomainEventBus eventBus) {
        this.cardRepository = cardRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateCardInput input, CreateCardOutput output) {
        Card card = new Card(
                input.getWorkflowId(),
                input.getStageId(),
                input.getCardName(),
                input.getDescription(),
                input.getType(),
                input.getSize()
        );

        cardRepository.add(CardEntityModelMapper.transformModelToEntity(card));
        eventBus.postAll(card);

        output.setCardId(card.getCardId());
        output.setCardName(card.getName());
    }

}
