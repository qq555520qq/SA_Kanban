package kanban.domain.usecase.card.commit;

import kanban.domain.model.DomainEventBus;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.workflow.mapper.WorkflowEntityModelMapper;
import kanban.domain.usecase.workflow.IWorkflowRepository;

public class CommitCardUseCase {

    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public CommitCardUseCase(IWorkflowRepository workflowRepository, DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.eventBus = eventBus;
    }

    public void execute(CommitCardInput input, CommitCardOutput output) {
        Workflow workflow = WorkflowEntityModelMapper.transformEntityToModel(
                workflowRepository.getWorkflowById(input.getWorkflowId()));

        String cardId = workflow.commitCardInStage(input.getCardId(), input.getStageId());
        workflowRepository.save(WorkflowEntityModelMapper.transformModelToEntity(workflow));
        eventBus.postAll(workflow);
        output.setCardId(cardId);
    }
}
