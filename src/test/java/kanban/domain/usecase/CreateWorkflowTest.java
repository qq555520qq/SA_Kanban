package kanban.domain.usecase;

import kanban.domain.usecase.workflow.CreateWorkflowInput;
import kanban.domain.usecase.workflow.CreateWorkflowOutput;
import kanban.domain.usecase.workflow.CreateWorkflowUseCase;
import kanban.domain.usecase.workflow.WorkflowRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateWorkflowTest {

    @Test
    public void Create_workflow_should_success() {
        WorkflowRepository workflowRepository = new WorkflowRepository();
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository);

        CreateWorkflowInput input = new CreateWorkflowInput();
        input.setBoardId("boardId");
        input.setWorkflowName("workflow");

        CreateWorkflowOutput output = new CreateWorkflowOutput();
        createWorkflowUseCase.execute(input, output);

        assertEquals("workflow", output.getWorkflowName());
    }
}
