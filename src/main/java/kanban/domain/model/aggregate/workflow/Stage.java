package kanban.domain.model.aggregate.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Stage implements Cloneable {
    private String workflowId;
    private String stageId;
    private String name;
    private List<String> cardIds;

    public Stage() {
        cardIds = new ArrayList<>();
    }

    public Stage(String workflowId, String stageName) {
        this.workflowId = workflowId;
        stageId = UUID.randomUUID().toString();
        name = stageName;
        cardIds = new ArrayList<String>();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String commitCard(String cardId) {
        cardIds.add(cardId);
        return cardId;
    }

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

    public List<String> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<String> cardIds) {
        this.cardIds = cardIds;
    }

}
