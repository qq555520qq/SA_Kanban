package kanban.domain.usecase.stage.create;

import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.workflow.mapper.WorkflowEntityModelMapper;
import kanban.domain.usecase.workflow.IWorkflowRepository;

public class CreateStageUseCase implements CreateStageInput {
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    private String stageName;
    private String workflowId;
    private int wipLimit;
    private String layout;

    public CreateStageUseCase(IWorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateStageInput input, CreateStageOutput output) {

        Workflow workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(input.getWorkflowId()));

        String stageId = workflow.createStage(
                input.getStageName(),
                input.getWipLimit(),
                input.getLayout()
        );

        workflowRepository.save(WorkflowEntityModelMapper.transformModelToEntity(workflow));
        eventBus.postAll(workflow);
        output.setStageId(stageId);
    }

    @Override
    public String getStageName() {
        return stageName;
    }

    @Override
    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    @Override
    public int getWipLimit() {
        return wipLimit;
    }

    @Override
    public void setWipLimit(int wipLimit) {
        this.wipLimit = wipLimit;
    }

    @Override
    public String getLayout() {
        return layout;
    }

    @Override
    public void setLayout(String layout) {
        this.layout = layout;
    }
}
