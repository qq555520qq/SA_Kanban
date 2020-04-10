package kanban.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Workflow {
    private String name;
    private String boardId;
    private String workflowId;
    private List<Stage> stages;

    public Workflow(String name, String boardId) {
        this.name = name;
        this.boardId = boardId;
        workflowId = UUID.randomUUID().toString();
        stages = new ArrayList<Stage>();
    }

    public String getName() {
        return name;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String createStage(String stageName) {
        Stage stage = new Stage(workflowId, stageName);
        stages.add(stage);
        return stage.getStageId();
    }

    public String getStageNameById(String stageId) {
        for (Stage each : stages) {
            if (each.getStageId().equalsIgnoreCase(stageId)) {
                return each.getStageName();
            }
        }
        throw new RuntimeException("Stage is not found,id=" + stageId);
    }
}
