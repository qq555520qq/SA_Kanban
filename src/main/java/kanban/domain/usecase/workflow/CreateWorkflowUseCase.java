package kanban.domain.usecase.workflow;

import kanban.domain.model.Workflow;

public class CreateWorkflowUseCase {
    private WorkflowRepository workflowRepository;

    public CreateWorkflowUseCase(WorkflowRepository workflowRepository){
        this.workflowRepository= workflowRepository;
    }

    public void execute(CreateWorkflowInput input, CreateWorkflowOutput output) {

        Workflow workflow= new Workflow(input.getWorkflowName(), input.getBoardId());
        workflowRepository.add(workflow);
        output.setWorkflowName(workflow.getName());
        output.setWorkflowId(workflow.getWorkflowId());
    }
}
