package kanban.domain.usecase.workflow;

import kanban.domain.Utility;
import kanban.domain.adapter.presenter.workflow.create.CreateWorkflowPresenter;
import kanban.domain.adapter.repository.board.InMemoryBoardRepository;
import kanban.domain.adapter.repository.card.InMemoryCardRepository;
import kanban.domain.adapter.repository.domainEvent.InMemoryDomainEventRepository;
import kanban.domain.adapter.repository.flowEvent.InMemoryFlowEventRepository;
import kanban.domain.adapter.repository.workflow.InMemoryWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.board.Board;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.card.ICardRepository;
import kanban.domain.usecase.flowEvent.IFlowEventRepository;
import kanban.domain.usecase.handler.domainEvent.DomainEventHandler;
import kanban.domain.usecase.board.mapper.BoardEntityModelMapper;
import kanban.domain.usecase.board.IBoardRepository;
import kanban.domain.usecase.handler.workflow.WorkflowEventHandler;
import kanban.domain.usecase.workflow.create.CreateWorkflowInput;
import kanban.domain.usecase.workflow.create.CreateWorkflowOutput;
import kanban.domain.usecase.workflow.create.CreateWorkflowUseCase;
import kanban.domain.usecase.workflow.mapper.WorkflowEntityModelMapper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateWorkflowTest {

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;
    private Utility utility;
    private String boardId;
    private IFlowEventRepository flowEventRepository;
    private ICardRepository cardRepository;

    @Before
    public void setUp() {
        boardRepository = new InMemoryBoardRepository();
        workflowRepository = new InMemoryWorkflowRepository();
        flowEventRepository = new InMemoryFlowEventRepository();
        cardRepository = new InMemoryCardRepository();
//        boardRepository = new MySqlBoardRepository();
//        workflowRepository = new MySqlWorkflowRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(new InMemoryDomainEventRepository()));
        eventBus.register(new WorkflowEventHandler(
                boardRepository,
                eventBus));


        utility = new Utility(boardRepository, workflowRepository, flowEventRepository, cardRepository,eventBus);
        boardId = utility.createBoard("test automation");
    }

    @Test
    public void Create_workflow_should_commit_workflow_in_its_board() {
        Board board = BoardEntityModelMapper.transformEntityToModel(boardRepository.getBoardById(boardId));

        assertEquals(0, board.getWorkflowIds().size());

        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(
                workflowRepository,
                eventBus
        );

        CreateWorkflowInput input = createWorkflowUseCase;
        CreateWorkflowOutput output = new CreateWorkflowPresenter();

        input.setBoardId(boardId);
        input.setWorkflowName("workflowName");

        createWorkflowUseCase.execute(input, output);
        assertNotNull(output.getWorkflowId());

        Workflow workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(output.getWorkflowId()));

        assertEquals("workflowName", workflow.getName());

        board = BoardEntityModelMapper.transformEntityToModel(boardRepository.getBoardById(boardId));

        assertEquals(1, board.getWorkflowIds().size());
    }

    @Test(expected = RuntimeException.class)
    public void Workflow_repository_should_throw_exception_when_workflow_not_found() {
        utility.createWorkflow(boardId, "workflowName");
        workflowRepository.getWorkflowById("123-456-789");
    }
}
