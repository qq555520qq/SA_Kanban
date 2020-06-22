package kanban.domain.usecase.card.move;

import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.workflow.mapper.WorkflowEntityModelMapper;
import kanban.domain.usecase.workflow.IWorkflowRepository;

public class MoveCardUseCase implements MoveCardInput {
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    private String workflowId;
    private String originStageId;
    private String newStageId;
    private String cardId;

    public MoveCardUseCase(IWorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    public void execute(MoveCardInput input, MoveCardOutput output) {
        Workflow workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(input.getWorkflowId()));

        String _cardId = workflow.moveCard(input.getCardId(),
                                    input.getOriginStageId(),
                                    input.getNewStageId());

        workflowRepository.save(WorkflowEntityModelMapper.transformModelToEntity(workflow));
        eventBus.postAll(workflow);
        output.setCardId(_cardId);
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
    public String getOriginStageId() {
        return originStageId;
    }

    @Override
    public void setOriginStageId(String originStageId) {
        this.originStageId = originStageId;
    }

    @Override
    public String getNewStageId() {
        return newStageId;
    }

    @Override
    public void setNewStageId(String newStageId) {
        this.newStageId = newStageId;
    }

    @Override
    public String getCardId() {
        return cardId;
    }

    @Override
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }


}
