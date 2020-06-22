package kanban.domain.usecase.stage.create;

public interface CreateStageInput {

    public String getStageName();

    public void setStageName(String stageName);

    public String getWorkflowId();

    public void setWorkflowId(String workflowId);

    public int getWipLimit();

    public void setWipLimit(int wipLimit);

    public String getLayout();

    public void setLayout(String layout);
}
