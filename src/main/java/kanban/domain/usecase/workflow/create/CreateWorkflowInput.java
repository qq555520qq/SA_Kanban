package kanban.domain.usecase.workflow.create;

public interface CreateWorkflowInput {

    public String getWorkflowName();

    public void setWorkflowName(String workflowName) ;

    public String getBoardId();

    public void setBoardId(String boardId);
}
