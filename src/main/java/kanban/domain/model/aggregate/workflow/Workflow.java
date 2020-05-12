package kanban.domain.model.aggregate.workflow;

import kanban.domain.model.aggregate.AggregateRoot;
import kanban.domain.model.aggregate.workflow.event.CardCommitted;
import kanban.domain.model.aggregate.workflow.event.StageCreated;
import kanban.domain.model.aggregate.workflow.event.WorkflowCreated;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Workflow extends AggregateRoot {
    private String name;
    private String workflowId;
    private List<Stage> stages;

    public Workflow() {
        stages = new ArrayList<>();
    }

    public Workflow(String boardId, String name) {
        this.name = name;
        workflowId = UUID.randomUUID().toString();
        stages = new ArrayList<Stage>();
        addDomainEvent(new WorkflowCreated(boardId, workflowId, name));
    }

    public String createStage(String stageName) {
        Stage stage = new Stage(workflowId, stageName);
        stages.add(stage);
        addDomainEvent(new StageCreated(workflowId, stage.getStageId(), stageName));
        return stage.getStageId();
    }


    public Stage getStageCloneById(String stageId) {
        Stage stage = getStageById(stageId);
        try {
            stage = (Stage) stage.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return stage;
    }

    private Stage getStageById(String stageId) {
        for (Stage each : stages) {
            if (each.getStageId().equalsIgnoreCase(stageId)) {
                return each;
            }
        }
        throw new RuntimeException("Stage is not found,id=" + stageId);
    }

    public String commitCardInStage(String cardId, String stageId) {
        Stage stage = getStageById(stageId);
        String _cardId = stage.commitCard(cardId);
        addDomainEvent(new CardCommitted(stageId, cardId));
        return _cardId;
    }

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

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }
}
