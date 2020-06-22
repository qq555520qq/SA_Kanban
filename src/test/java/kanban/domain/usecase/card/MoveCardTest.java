package kanban.domain.usecase.card;

import kanban.domain.Utility;
import kanban.domain.adapter.presenter.card.move.MoveCardPresenter;
import kanban.domain.adapter.repository.board.InMemoryBoardRepository;
import kanban.domain.adapter.repository.card.InMemoryCardRepository;
import kanban.domain.adapter.repository.domainEvent.InMemoryDomainEventRepository;
import kanban.domain.adapter.repository.flowEvent.InMemoryFlowEventRepository;
import kanban.domain.adapter.repository.workflow.InMemoryWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.board.IBoardRepository;
import kanban.domain.usecase.card.move.MoveCardInput;
import kanban.domain.usecase.card.move.MoveCardOutput;
import kanban.domain.usecase.card.move.MoveCardUseCase;
import kanban.domain.usecase.domainEvent.IDomainEventRepository;
import kanban.domain.usecase.flowEvent.IFlowEventRepository;
import kanban.domain.usecase.handler.card.CardEventHandler;
import kanban.domain.usecase.handler.domainEvent.DomainEventHandler;
import kanban.domain.usecase.handler.workflow.WorkflowEventHandler;
import kanban.domain.usecase.workflow.mapper.WorkflowEntityModelMapper;
import kanban.domain.usecase.workflow.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoveCardTest {
    private String boardId;
    private String workflowId;
    private String todoStageId;
    private String doingStageId;
    private String doneStageId;
    private String cardId;

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;
    private IFlowEventRepository flowEventRepository;
    private ICardRepository cardRepository;

    private IDomainEventRepository domainEventRepository;

    private Utility utility;

    @Before
    public void setUp() throws Exception {
        boardRepository = new InMemoryBoardRepository();
        workflowRepository = new InMemoryWorkflowRepository();
        domainEventRepository = new InMemoryDomainEventRepository();
        flowEventRepository = new InMemoryFlowEventRepository();
        cardRepository = new InMemoryCardRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new WorkflowEventHandler(boardRepository, eventBus));
        eventBus.register(new CardEventHandler(boardRepository, workflowRepository, eventBus));
        eventBus.register(new DomainEventHandler(domainEventRepository));

        utility = new Utility(boardRepository, workflowRepository, flowEventRepository, cardRepository,eventBus);
        boardId = utility.createBoard("test automation");
        workflowId = utility.createWorkflow(boardId,"workflowName");
        todoStageId = utility.createStage(workflowId,"todo");
        doingStageId = utility.createStage(workflowId,"doing");
        doneStageId = utility.createStage(workflowId,"done");
        cardId = utility.createCard(workflowId, todoStageId, "cardName",
                                "description", "general", "xxl");
    }

    @Test
    public void Card_should_be_moved_from_todoStage_to_doingStage() {
        Workflow workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(workflowId));

        assertEquals(1, workflow.getStageCloneById(todoStageId).getCardIds().size());
        assertEquals(0, workflow.getStageCloneById(doingStageId).getCardIds().size());

        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(workflowRepository, eventBus);
        MoveCardInput input = moveCardUseCase;
        input.setCardId(cardId);
        input.setWorkflowId(workflowId);
        input.setOriginStageId(todoStageId);
        input.setNewStageId(doingStageId);
        MoveCardOutput output = new MoveCardPresenter();

        moveCardUseCase.execute(input, output);

        assertEquals(cardId, output.getCardId());

        workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(workflowId));

        assertEquals(0, workflow.getStageCloneById(todoStageId).getCardIds().size());
        assertEquals(1, workflow.getStageCloneById(doingStageId).getCardIds().size());
    }

    @Test
    public void Card_should_be_moved_from_doingStage_to_doneStage() {
        Card_should_be_moved_from_todoStage_to_doingStage();

        Workflow workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(workflowId));

        assertEquals(1, workflow.getStageCloneById(doingStageId).getCardIds().size());
        assertEquals(0, workflow.getStageCloneById(doneStageId).getCardIds().size());

        String _cardId = utility.moveCard(workflowId, cardId, doingStageId, doneStageId);

        assertEquals(cardId, _cardId);

        workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(workflowId));

        assertEquals(0, workflow.getStageCloneById(doingStageId).getCardIds().size());
        assertEquals(1, workflow.getStageCloneById(doneStageId).getCardIds().size());
    }

    @Test
    public void Card_should_be_moved_from_doneStage_to_todoStage() {
        Card_should_be_moved_from_doingStage_to_doneStage();

        Workflow workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(workflowId));

        assertEquals(1, workflow.getStageCloneById(doneStageId).getCardIds().size());
        assertEquals(0, workflow.getStageCloneById(todoStageId).getCardIds().size());

        String _cardId = utility.moveCard(workflowId, cardId, doneStageId, todoStageId);

        assertEquals(cardId, _cardId);

        workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(workflowId));

        assertEquals(0, workflow.getStageCloneById(doneStageId).getCardIds().size());
        assertEquals(1, workflow.getStageCloneById(todoStageId).getCardIds().size());
    }
}
