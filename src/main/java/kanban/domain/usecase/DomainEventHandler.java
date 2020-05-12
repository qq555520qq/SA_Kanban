package kanban.domain.usecase;

import com.google.common.eventbus.Subscribe;
import kanban.domain.model.aggregate.card.event.CardCreated;
import kanban.domain.model.aggregate.workflow.event.WorkflowCreated;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.card.commit.CommitCardInput;
import kanban.domain.usecase.card.commit.CommitCardOutput;
import kanban.domain.usecase.card.commit.CommitCardUseCase;
import kanban.domain.usecase.workflow.commit.CommitWorkflowInput;
import kanban.domain.usecase.workflow.commit.CommitWorkflowOutput;
import kanban.domain.usecase.workflow.commit.CommitWorkflowUseCase;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;

public class DomainEventHandler {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;

    public DomainEventHandler(IBoardRepository boardRepository,
                              IWorkflowRepository workflowRepository) {
        this.boardRepository = boardRepository;
        this.workflowRepository = workflowRepository;
    }

    @Subscribe
    public void handleEvent(WorkflowCreated workflowCreated) {
        CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository);
        CommitWorkflowInput commitWorkflowInput = new CommitWorkflowInput();
        CommitWorkflowOutput commitWorkflowOutput = new CommitWorkflowOutput();

        commitWorkflowInput.setBoardId(workflowCreated.getBoardId());
        commitWorkflowInput.setWorkflowId("workflowId");

        commitWorkflowUseCase.execute(commitWorkflowInput, commitWorkflowOutput);
    }

    @Subscribe
    public void handleEvent(CardCreated cardCreated) {
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository);
        CommitCardInput commitCardInput = new CommitCardInput();
        commitCardInput.setCardId(cardCreated.getCardId());
        commitCardInput.setStageId(cardCreated.getStageId());
        commitCardInput.setWorkflowId(cardCreated.getWorkflowId());
        CommitCardOutput commitCardOutput = new CommitCardOutput();

        commitCardUseCase.execute(commitCardInput, commitCardOutput);
    }


}
