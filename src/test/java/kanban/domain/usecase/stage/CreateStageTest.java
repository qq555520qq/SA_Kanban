package kanban.domain.usecase.stage;

import kanban.domain.Utility;
import kanban.domain.adapter.presenter.stage.create.CreateStagePresenter;
import kanban.domain.adapter.repository.board.InMemoryBoardRepository;
import kanban.domain.adapter.repository.card.InMemoryCardRepository;
import kanban.domain.adapter.repository.domainEvent.InMemoryDomainEventRepository;
import kanban.domain.adapter.repository.flowEvent.InMemoryFlowEventRepository;
import kanban.domain.adapter.repository.workflow.InMemoryWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.workflow.Stage;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.card.ICardRepository;
import kanban.domain.usecase.flowEvent.IFlowEventRepository;
import kanban.domain.usecase.handler.domainEvent.DomainEventHandler;
import kanban.domain.usecase.board.IBoardRepository;
import kanban.domain.usecase.stage.create.CreateStageInput;
import kanban.domain.usecase.stage.create.CreateStageOutput;
import kanban.domain.usecase.stage.create.CreateStageUseCase;
import kanban.domain.usecase.workflow.mapper.WorkflowEntityModelMapper;
import kanban.domain.usecase.workflow.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class CreateStageTest {
    private String boardId;
    private String workflowId;
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;
    private Utility utility;
    private IFlowEventRepository flowEventRepository;
    private ICardRepository cardRepository;

    @Before
    public void setup() {
        boardRepository = new InMemoryBoardRepository();
        workflowRepository = new InMemoryWorkflowRepository();
        flowEventRepository = new InMemoryFlowEventRepository();
        cardRepository = new InMemoryCardRepository();
//        boardRepository = new MySqlBoardRepository();
//        workflowRepository = new MySqlWorkflowRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(new InMemoryDomainEventRepository()));

        utility = new Utility(boardRepository, workflowRepository, flowEventRepository, cardRepository,eventBus);
        boardId = utility.createBoard("test automation");
        workflowId = utility.createWorkflow(boardId, "workflowName");
    }

    @Test
    public void Create_stage_should_return_stageId() {
        Workflow workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(workflowId));

        assertEquals(0,workflow.getStages().size());

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, eventBus);
        CreateStageInput input = createStageUseCase;
        input.setStageName("stageName");
        input.setWorkflowId(workflowId);
        input.setWipLimit(1);
        input.setLayout("Horizontal");
        CreateStageOutput output = new CreateStagePresenter();

        createStageUseCase.execute(input, output);

        assertNotNull(output.getStageId());

        workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(workflowId));

        assertEquals(1,workflow.getStages().size());

        Stage stage = workflow.getStageCloneById(output.getStageId());

        assertEquals("stageName", stage.getName());
        assertEquals(workflowId, stage.getWorkflowId());
        assertEquals(1, stage.getWipLimit().toInt());
        assertEquals("Horizontal", stage.getLayout().toString());

        assertEquals(0, stage.getStages().size());
        assertEquals(0, stage.getCardIds().size());
    }

    @Test(expected = RuntimeException.class)
    public void GetStageNameById_should_throw_exception() {
        Workflow workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(workflowId));
        workflow.getStageCloneById("123-456-789");
    }
}
