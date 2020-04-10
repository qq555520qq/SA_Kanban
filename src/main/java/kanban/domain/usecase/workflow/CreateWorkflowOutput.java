package kanban.domain.usecase.workflow;

public class CreateWorkflowOutput {
    private String workflowName;

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    private String workflowId;

    public String getWorkflowName() {
        return workflowName;
    }
    public void setWorkflowName(String workflowName){
        this.workflowName= workflowName;
    }
}
