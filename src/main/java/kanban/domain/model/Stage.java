package kanban.domain.model;

import java.util.UUID;

public class Stage {
    private String workflowId;
    private String stageId;
    private String name;

    public Stage(String workflowId, String stageName) {
        this.workflowId = workflowId;
        stageId = UUID.randomUUID().toString();
        name = stageName;
    }

    public String getStageId() {
        return stageId;
    }

    public String getStageName() {
        return name;
    }
}
