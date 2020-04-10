package kanban.domain;

import kanban.domain.usecase.stage.CreateStageInput;
import kanban.domain.usecase.stage.CreateStageOutput;
import kanban.domain.usecase.stage.CreateStageUseCase;
import kanban.domain.usecase.workflow.CreateWorkflowInput;
import kanban.domain.usecase.workflow.CreateWorkflowOutput;
import kanban.domain.usecase.workflow.CreateWorkflowUseCase;
import kanban.domain.usecase.workflow.WorkflowRepository;

public class Utility {

    WorkflowRepository workflowRepository;

    public Utility(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public String createWorkflow(String boardId, String workflowName){
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository);

        CreateWorkflowInput input = new CreateWorkflowInput();
        input.setBoardId(boardId);
        input.setWorkflowName(workflowName);

        CreateWorkflowOutput output = new CreateWorkflowOutput();
        createWorkflowUseCase.execute(input, output);
        return output.getWorkflowId();
    }

    public String createStage(String workflowId, String stageName) {

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository);
        CreateStageInput input = new CreateStageInput();
        input.setStageName(stageName);
        input.setWorkflowId(workflowId);

        CreateStageOutput output = new CreateStageOutput();
        createStageUseCase.execute(input, output);
        return output.getStageId();
    }
}
