package kanban.domain.adapter.repository.workflow.data;

import java.util.List;

public class WorkflowData {
    private String name;
    private String workflowId;
    private List<StageData> stageDatas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public List<StageData> getStageDatas() {
        return stageDatas;
    }

    public void setStageDatas(List<StageData> stageDatas) {
        this.stageDatas = stageDatas;
    }
}
