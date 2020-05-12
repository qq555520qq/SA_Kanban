package kanban.domain.usecase.card.create;

import kanban.domain.model.DomainEvent;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.card.Card;
import kanban.domain.usecase.card.commit.CommitCardInput;
import kanban.domain.usecase.card.commit.CommitCardOutput;
import kanban.domain.usecase.card.commit.CommitCardUseCase;
import kanban.domain.usecase.card.repository.ICardRepository;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;

public class CreateCardUseCase {

    private IWorkflowRepository workflowRepository;
    private ICardRepository cardRepository;
    private DomainEventBus eventBus;
    public CreateCardUseCase(IWorkflowRepository workflowRepository,
                             ICardRepository cardRepository,
                             DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
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

        cardRepository.add(card);

        output.setCardId(card.getCardId());
        output.setCardName(card.getName());

        eventBus.postAll(card);

    }

}
