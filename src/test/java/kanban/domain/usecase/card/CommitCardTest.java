package kanban.domain.usecase.card;

import kanban.domain.Utility;
import kanban.domain.adapter.presenter.card.commit.CommitCardPresenter;
import kanban.domain.adapter.repository.board.InMemoryBoardRepository;
import kanban.domain.adapter.repository.domainEvent.InMemoryDomainEventRepository;
import kanban.domain.adapter.repository.workflow.InMemoryWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.flowEvent.IFlowEventRepository;
import kanban.domain.usecase.handler.domainEvent.DomainEventHandler;
import kanban.domain.usecase.board.IBoardRepository;
import kanban.domain.usecase.card.commit.CommitCardInput;
import kanban.domain.usecase.card.commit.CommitCardUseCase;
import kanban.domain.usecase.workflow.mapper.WorkflowEntityModelMapper;
import kanban.domain.usecase.workflow.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommitCardTest {
    private String boardId;
    private String workflowId;
    private String stageId;
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;
    private IFlowEventRepository flowEventRepository;
    private ICardRepository cardRepository;
    private Utility utility;

    @Before
    public void setup() {
        boardRepository = new InMemoryBoardRepository();
        workflowRepository = new InMemoryWorkflowRepository();
//        boardRepository = new MySqlBoardRepository();
//        workflowRepository = new MySqlWorkflowRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(new InMemoryDomainEventRepository()));

        utility = new Utility(boardRepository, workflowRepository, flowEventRepository, cardRepository,eventBus);
        boardId = utility.createBoard("test automation");
        workflowId = utility.createWorkflow(boardId,"workflowName");
        stageId = utility.createStage(workflowId,"stageName");
    }

    @Test
    public void Card_should_be_committed_in_its_stage() {
        Workflow workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(workflowId));

        assertEquals(0,workflow.getStageCloneById(stageId).getCardIds().size());

        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository, eventBus);
        CommitCardInput input = new CommitCardInput();
        input.setCardId("cardId");
        input.setWorkflowId(workflowId);
        input.setStageId(stageId);
        CommitCardPresenter output = new CommitCardPresenter();

        commitCardUseCase.execute(input, output);
        workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(workflowId));

        assertEquals(1,workflow.getStageCloneById(stageId).getCardIds().size());
        assertEquals("cardId", output.getCardId());
    }
}
