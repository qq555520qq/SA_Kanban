package kanban.domain.usecase.workflow.create;

import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;

public class CreateWorkflowUseCase {

    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;


    public CreateWorkflowUseCase(
            IWorkflowRepository workflowRepository,
            DomainEventBus eventBus){
        this.workflowRepository= workflowRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateWorkflowInput input, CreateWorkflowOutput output) {

        Workflow workflow= new Workflow(input.getBoardId(), input.getWorkflowName());
        workflowRepository.add(workflow);
        output.setWorkflowName(workflow.getName());
        output.setWorkflowId(workflow.getWorkflowId());

        eventBus.postAll(workflow);
    }
}
