package kanban.domain.usecase.workflow.create;

public class CreateWorkflowInput {
    private String boardId;
    private String workflowName;

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getBoardId(){
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId= boardId;
    }
}
