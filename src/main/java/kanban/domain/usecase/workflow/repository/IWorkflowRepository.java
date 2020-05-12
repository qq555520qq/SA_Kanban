package kanban.domain.usecase.workflow.repository;

import kanban.domain.model.aggregate.workflow.Workflow;

public interface IWorkflowRepository {

    void add(Workflow workflow);

    Workflow getWorkflowById(String workflowId);

    void save(Workflow workflow);
}
