package kanban.domain.usecase.handler.card;

import com.google.common.eventbus.Subscribe;
import kanban.domain.adapter.presenter.card.commit.CommitCardPresenter;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.card.event.CardCreated;
import kanban.domain.usecase.board.IBoardRepository;
import kanban.domain.usecase.card.commit.CommitCardInput;
import kanban.domain.usecase.card.commit.CommitCardUseCase;
import kanban.domain.usecase.workflow.IWorkflowRepository;

public class CardEventHandler {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public CardEventHandler(IBoardRepository boardRepository,
                              IWorkflowRepository workflowRepository,
                              DomainEventBus eventBus) {
        this.boardRepository = boardRepository;
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    @Subscribe
    public void handleEvent(CardCreated cardCreated) {
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository, eventBus);
        CommitCardInput commitCardInput = new CommitCardInput();
        commitCardInput.setCardId(cardCreated.getCardId());
        commitCardInput.setStageId(cardCreated.getStageId());
        commitCardInput.setWorkflowId(cardCreated.getWorkflowId());
        CommitCardPresenter commitCardOutput = new CommitCardPresenter();

        commitCardUseCase.execute(commitCardInput, commitCardOutput);
    }
}
