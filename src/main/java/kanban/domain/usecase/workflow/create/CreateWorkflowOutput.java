package kanban.domain.usecase.workflow.create;

public class CreateWorkflowOutput {

    private String workflowId;
    private String workflowName;

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName){
        this.workflowName= workflowName;
    }
}
