package kanban.domain.usecase.card;

import kanban.domain.model.Card;
import kanban.domain.model.Workflow;
import kanban.domain.usecase.stage.CreateStageInput;
import kanban.domain.usecase.stage.CreateStageOutput;
import kanban.domain.usecase.workflow.WorkflowRepository;

public class CreateCardUseCase {
    private CardRepository cardRepository;

    public CreateCardUseCase(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void execute(CreateCardInput input, CreateCardOutput output) {
        Card card = new Card(input.getCardName(),input.getStageId());
        cardRepository.add(card);
        output.setCardId(card.getCardId());
        output.setCardName(card.getCardName());
    }

}
