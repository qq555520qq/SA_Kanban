package kanban.domain.usecase.stage;

import com.google.common.eventbus.EventBus;
import kanban.domain.Utility;
import kanban.domain.adapter.repository.board.MySqlBoardRepository;
import kanban.domain.adapter.repository.workflow.MySqlWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.workflow.Stage;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.DomainEventHandler;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.stage.create.CreateStageInput;
import kanban.domain.usecase.stage.create.CreateStageOutput;
import kanban.domain.usecase.stage.create.CreateStageUseCase;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateStageTest {
    private String boardId;
    private String workflowId;
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;
    private Utility utility;

    @Before
    public void setup() {
//        boardRepository = new InMemoryBoardRepository();
//        workflowRepository = new InMemoryWorkflowRepository();
        boardRepository = new MySqlBoardRepository();
        workflowRepository = new MySqlWorkflowRepository();


        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(
                boardRepository,
                workflowRepository));

        utility = new Utility(boardRepository, workflowRepository, eventBus);
        boardId = utility.createBoard("test automation");
        workflowId = utility.createWorkflow(boardId, "workflowName");
    }

    @Test
    public void Create_stage_should_return_stageId() {
        Workflow workflow = workflowRepository.getWorkflowById(workflowId);

        assertEquals(0,workflow.getStages().size());

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository);
        CreateStageInput input = new CreateStageInput();
        input.setStageName("stage");
        input.setWorkflowId(workflowId);
        CreateStageOutput output = new CreateStageOutput();

        createStageUseCase.execute(input, output);

        workflow = workflowRepository.getWorkflowById(workflowId);

        assertEquals(1,workflow.getStages().size());

        Stage cloneStage = workflow.getStageCloneById(output.getStageId());

        assertEquals(output.getStageName(), cloneStage.getName());
    }

    @Test(expected = RuntimeException.class)
    public void GetStageNameById_should_throw_exception() {
        Workflow workflow = workflowRepository.getWorkflowById(workflowId);
        workflow.getStageCloneById("123-456-789");
    }
}
