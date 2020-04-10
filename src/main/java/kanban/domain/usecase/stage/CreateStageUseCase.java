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
        output.setStageId(workflow.createStage(input.getStageName()));
        output.setStageName(workflow.getStageNameById(output.getStageId()));
        workflowRepository.save(workflow);
    }
}
