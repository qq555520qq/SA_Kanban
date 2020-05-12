package kanban.domain.usecase;

import kanban.domain.Utility;
import kanban.domain.adapter.repository.board.InMemoryBoardRepository;
import kanban.domain.adapter.repository.board.MySqlBoardRepository;
import kanban.domain.adapter.repository.card.InMemoryCardRepository;
import kanban.domain.adapter.repository.card.MySqlCardRepository;
import kanban.domain.adapter.repository.workflow.InMemoryWorkflowRepository;
import kanban.domain.adapter.repository.workflow.MySqlWorkflowRepository;
import kanban.domain.model.DomainEvent;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.card.Card;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.card.create.CreateCardInput;
import kanban.domain.usecase.card.create.CreateCardOutput;
import kanban.domain.usecase.card.create.CreateCardUseCase;
import kanban.domain.usecase.card.repository.ICardRepository;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;
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
    private ICardRepository cardRepository;
    private DomainEventBus eventBus;
    private Utility utility;

    @Before
    public void setup() {
//        boardRepository = new InMemoryBoardRepository();
//        workflowRepository = new InMemoryWorkflowRepository();
        boardRepository = new MySqlBoardRepository();
        workflowRepository = new MySqlWorkflowRepository();
        cardRepository = new MySqlCardRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(
                boardRepository,
                workflowRepository));

        utility = new Utility(boardRepository, workflowRepository, eventBus);
        boardId = utility.createBoard("test automation");
        workflowId = utility.createWorkflow(boardId,"workflowName");
        stageId = utility.createStage(workflowId,"stageName");
    }

    @Test
    public void Create_card_should_commit_card_in_its_stage() {
        Workflow workflow = workflowRepository.getWorkflowById(workflowId);
        assertEquals(0, workflow.getStageCloneById(stageId).getCardIds().size());

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(
                workflowRepository,
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
        CreateCardOutput output = new CreateCardOutput();

        createCardUseCase.execute(input, output);

        Card card = cardRepository.getCardById(output.getCardId());
        assertEquals(output.getCardName(), card.getName());
        assertEquals("description", card.getDescription());
        assertEquals("general", card.getType());
        assertEquals("xxl", card.getSize());

        workflow = workflowRepository.getWorkflowById(workflowId);
        assertEquals(1, workflow.getStageCloneById(stageId).getCardIds().size());
    }
}
