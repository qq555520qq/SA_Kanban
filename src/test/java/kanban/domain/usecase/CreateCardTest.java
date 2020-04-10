package kanban.domain.usecase;

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

public class CreateCardTest {

    private String workflowId;
    private String stageId;
    private WorkflowRepository workflowRepository = new WorkflowRepository();

    @Before
    public void setup() {
        workflowId = create_workflow();
        stageId = create_stage();
    }

    @Test
    public void Create_card_should_success() {

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository);
        CreateCardInput input = new CreateCardInput();
        input.setCardName("card");
        input.setStageId(stageId);

        CreateCardOutput output = new CreateCardOutput();
        CreateCardUseCase.execute(input, output);
        assertEquals("card", output.getCardName());
        assertNotNull(output.getCardId());
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

    public String create_stage() {

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository);
        CreateStageInput input = new CreateStageInput();
        input.setStageName("stage");
        input.setWorkflowId(workflowId);

        CreateStageOutput output = new CreateStageOutput();
        createStageUseCase.execute(input, output);
        return output.getStageId();
    }
}
