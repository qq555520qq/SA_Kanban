package kanban.domain.usecase.workflow;

import kanban.domain.usecase.stage.StageEntity;

import java.util.List;

public class WorkflowEntity {
    private String name;
    private String workflowId;
    private List<StageEntity> stageEntities;

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

    public List<StageEntity> getStageEntities() {
        return stageEntities;
    }

    public void setStageEntities(List<StageEntity> stageEntities) {
        this.stageEntities = stageEntities;
    }
}
