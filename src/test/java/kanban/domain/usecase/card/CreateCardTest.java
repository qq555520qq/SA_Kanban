package kanban.domain.usecase.card;

import kanban.domain.Utility;
import kanban.domain.adapter.presenter.card.create.CreateCardPresenter;
import kanban.domain.adapter.repository.board.InMemoryBoardRepository;
import kanban.domain.adapter.repository.card.InMemoryCardRepository;
import kanban.domain.adapter.repository.domainEvent.InMemoryDomainEventRepository;
import kanban.domain.adapter.repository.workflow.InMemoryWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.card.Card;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.flowEvent.IFlowEventRepository;
import kanban.domain.usecase.handler.card.CardEventHandler;
import kanban.domain.usecase.handler.domainEvent.DomainEventHandler;
import kanban.domain.usecase.board.IBoardRepository;
import kanban.domain.usecase.card.create.CreateCardInput;
import kanban.domain.usecase.card.create.CreateCardUseCase;
import kanban.domain.usecase.card.mapper.CardEntityModelMapper;
import kanban.domain.usecase.workflow.mapper.WorkflowEntityModelMapper;
import kanban.domain.usecase.workflow.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateCardTest {

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
        cardRepository = new InMemoryCardRepository();
//        boardRepository = new MySqlBoardRepository();
//        workflowRepository = new MySqlWorkflowRepository();
//        cardRepository = new MySqlCardRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(new InMemoryDomainEventRepository()));
        eventBus.register(new CardEventHandler(
                boardRepository,
                workflowRepository,
                eventBus));

        utility = new Utility(boardRepository, workflowRepository, flowEventRepository, cardRepository,eventBus);

        boardId = utility.createBoard("test automation");
        workflowId = utility.createWorkflow(boardId,"workflowName");
        stageId = utility.createStage(workflowId,"stageName");
    }

    @Test
    public void Create_card_should_commit_card_in_its_stage() {
        Workflow workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(workflowId));

        assertEquals(0, workflow.getStageCloneById(stageId).getCardIds().size());

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(
                cardRepository,
                eventBus
        );
        CreateCardInput input = new CreateCardInput();
        input.setCardName("card");
        input.setDescription("description");
        input.setType("general");
        input.setSize("xxl");
        input.setWorkflowId(workflowId);
        input.setStageId(stageId);
        CreateCardPresenter output = new CreateCardPresenter();

        createCardUseCase.execute(input, output);

        Card card = CardEntityModelMapper.transformEntityToModel(cardRepository.getCardById(output.getCardId()));
        assertEquals(output.getCardName(), card.getName());
        assertEquals("description", card.getDescription());
        assertEquals("general", card.getType());
        assertEquals("xxl", card.getSize());

        workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(workflowId));
        assertEquals(1, workflow.getStageCloneById(stageId).getCardIds().size());
    }
}
