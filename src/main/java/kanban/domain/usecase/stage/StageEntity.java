package kanban.domain.usecase.stage;

import java.util.List;

public class StageEntity {
    private String workflowId;
    private String stageId;
    private String name;
    private int wipLimit;
    private String layout;
    private List<StageEntity> stageEntities;
    private List<String> cardIds;

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWipLimit() {
        return wipLimit;
    }

    public void setWipLimit(int wipLimit) {
        this.wipLimit = wipLimit;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public List<StageEntity> getStageEntities() {
        return stageEntities;
    }

    public void setStageEntities(List<StageEntity> stageEntities) {
        this.stageEntities = stageEntities;
    }

    public List<String> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<String> cardIds) {
        this.cardIds = cardIds;
    }
}
