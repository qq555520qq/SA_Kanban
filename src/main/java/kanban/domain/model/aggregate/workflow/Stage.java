package kanban.domain.model.aggregate.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Stage implements Cloneable {
    private String workflowId;
    private String stageId;
    private String name;
    private WipLimit wipLimit;
    private Layout layout;
    private List<Stage> stages;
    private List<String> cardIds;

    public Stage() {
        stages = new ArrayList<>();
        cardIds = new ArrayList<>();
    }

    public Stage(String workflowId, String stageName, int wipLimit, String layout) {
        this.workflowId = workflowId;
        this.stageId = UUID.randomUUID().toString();
        this.name = stageName;
        this.wipLimit = new WipLimit(wipLimit);
        this.layout = new Layout(layout);
        this.stages = new ArrayList<>();
        this.cardIds = new ArrayList<>();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String commitCard(String cardId) {
        cardIds.add(cardId);
        return cardId;
    }


    public String unCommitCard(String cardId) {
        if(!cardIds.contains(cardId))
            throw new RuntimeException("CardId can't be found in cardIds, CardId:" + cardId );
        cardIds.remove(cardId);
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

    public WipLimit getWipLimit() {
        return wipLimit;
    }

    public void setWipLimit(WipLimit wipLimit) {
        this.wipLimit = wipLimit;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public List<String> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<String> cardIds) {
        this.cardIds = cardIds;
    }

}
