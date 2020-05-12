package kanban.domain.usecase.card.commit;

import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;

public class CommitCardUseCase {

    private IWorkflowRepository workflowRepository;

    public CommitCardUseCase(IWorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    public void execute(CommitCardInput input, CommitCardOutput output) {
        Workflow workflow = workflowRepository.getWorkflowById(input.getWorkflowId());
        String cardId = workflow.commitCardInStage(input.getCardId(), input.getStageId());
        workflowRepository.save(workflow);

        output.setCardId(cardId);
    }
}
