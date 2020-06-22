package kanban.domain.usecase.workflow;

public interface IWorkflowRepository {

    void add(WorkflowEntity workflowEntity);

    WorkflowEntity getWorkflowById(String workflowId);

    void save(WorkflowEntity workflowEntity);
}
