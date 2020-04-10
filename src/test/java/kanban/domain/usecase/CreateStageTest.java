package kanban.domain.usecase;

import kanban.domain.Utility;
import kanban.domain.model.Workflow;
import kanban.domain.usecase.stage.CreateStageInput;
import kanban.domain.usecase.stage.CreateStageOutput;
import kanban.domain.usecase.stage.CreateStageUseCase;
import kanban.domain.usecase.workflow.WorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateStageTest {
    private String workflowId;
    private WorkflowRepository workflowRepository;
    private Utility utility;

    @Before
    public void setup() {
        workflowRepository = new WorkflowRepository();
        utility = new Utility(workflowRepository);
        workflowId = utility.createWorkflow("boardId", "workflowName");
    }

    @Test
    public void Create_stage_should_success() {

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository);
        CreateStageInput input = new CreateStageInput();
        input.setStageName("stage");
        input.setWorkflowId(workflowId);

        CreateStageOutput output = new CreateStageOutput();
        createStageUseCase.execute(input, output);
        assertEquals("stage", output.getStageName());
        assertNotNull(output.getStageId());
    }

    @Test(expected = RuntimeException.class)
    public void GetStageNameById_should_throw_exception() {

        Workflow workflow = workflowRepository.getWorkflowById(workflowId);

        workflow.getStageNameById("12345678");

    }
}
