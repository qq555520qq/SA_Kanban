package kanban.domain.usecase.workflow.create;

import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.workflow.mapper.WorkflowEntityModelMapper;
import kanban.domain.usecase.workflow.IWorkflowRepository;

public class CreateWorkflowUseCase implements CreateWorkflowInput {

    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    private String workflowName;
    private String boardId;

    public CreateWorkflowUseCase(
            IWorkflowRepository workflowRepository,
            DomainEventBus eventBus){
        this.workflowRepository= workflowRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateWorkflowInput input, CreateWorkflowOutput output) {

        Workflow workflow= new Workflow(input.getBoardId(), input.getWorkflowName());
        workflowRepository.add(WorkflowEntityModelMapper.transformModelToEntity(workflow));

        eventBus.postAll(workflow);
        output.setWorkflowId(workflow.getWorkflowId());
    }

    @Override
    public String getWorkflowName() {
        return workflowName;
    }

    @Override
    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    @Override
    public String getBoardId() {
        return boardId;
    }

    @Override
    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
