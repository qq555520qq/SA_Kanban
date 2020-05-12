package kanban.domain.usecase.stage.create;

import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;

public class CreateStageUseCase {
    private IWorkflowRepository workflowRepository;

    public CreateStageUseCase(IWorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateStageInput input, CreateStageOutput output) {

        Workflow workflow = workflowRepository.getWorkflowById(input.getWorkflowId());
        String stageId = workflow.createStage(input.getStageName());
        output.setStageId(stageId);
        output.setStageName(workflow.getStageCloneById(output.getStageId()).getName());

        workflowRepository.save(workflow);
    }
}
