package kanban.domain.usecase.stage;

import kanban.domain.model.Workflow;
import kanban.domain.usecase.workflow.WorkflowRepository;

public class CreateStageUseCase {
    private WorkflowRepository workflowRepository;

    public CreateStageUseCase(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateStageInput input, CreateStageOutput output) {

        Workflow workflow = workflowRepository.getWorkflowById(input.getWorkflowId());
        String stageId = workflow.createStage(input.getStageName());
        output.setStageId(stageId);
        output.setStageName(workflow.getStageNameById(output.getStageId()));
        workflowRepository.save(workflow);
    }
}
