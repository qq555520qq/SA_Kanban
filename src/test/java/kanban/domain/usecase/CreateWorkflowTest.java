package kanban.domain.usecase;

import kanban.domain.Utility;
import kanban.domain.usecase.workflow.CreateWorkflowInput;
import kanban.domain.usecase.workflow.CreateWorkflowOutput;
import kanban.domain.usecase.workflow.CreateWorkflowUseCase;
import kanban.domain.usecase.workflow.WorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateWorkflowTest {

    private WorkflowRepository workflowRepository;
    private Utility utility;


    @Before
    public void setUp() throws Exception {
        workflowRepository = new WorkflowRepository();
        utility = new Utility(workflowRepository);
    }

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

    @Test(expected = RuntimeException.class)
    public void Workflow_repository_should_throw_exception() {
        utility.createWorkflow("12345","1234567");
        workflowRepository.getWorkflowById("12345678");

    }

}
