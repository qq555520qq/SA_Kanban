package kanban.domain.usecase.workflow.commit;

public interface CommitWorkflowInput {
    public String getBoardId();

    public String getWorkflowId();

    public void setBoardId(String boardId);

    public void setWorkflowId(String workflowId);
}