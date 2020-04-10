package kanban.domain.usecase;

import kanban.domain.model.Workflow;
import kanban.domain.usecase.stage.CreateStageInput;
import kanban.domain.usecase.stage.CreateStageOutput;
import kanban.domain.usecase.stage.CreateStageUseCase;
import kanban.domain.usecase.workflow.CreateWorkflowInput;
import kanban.domain.usecase.workflow.CreateWorkflowOutput;
import kanban.domain.usecase.workflow.CreateWorkflowUseCase;
import kanban.domain.usecase.workflow.WorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateStageTest {
    private String workflowId;
    private WorkflowRepository workflowRepository = new WorkflowRepository();

    @Before
    public void setup() {
        workflowId = create_workflow();
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

    public String create_workflow() {

        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository);

        CreateWorkflowInput input = new CreateWorkflowInput();
        input.setBoardId("boardId");
        input.setWorkflowName("workflow");

        CreateWorkflowOutput output = new CreateWorkflowOutput();
        createWorkflowUseCase.execute(input, output);
        return output.getWorkflowId();
    }
}
