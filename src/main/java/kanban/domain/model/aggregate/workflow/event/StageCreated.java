package kanban.domain.model.aggregate.workflow.event;

import kanban.domain.model.DomainEvent;
import kanban.domain.model.common.DateProvider;

import java.util.Date;

public class StageCreated implements DomainEvent {

    private Date occurredOn;
    private String workflowId;
    private String stageId;
    private String name;
    private int wipLimit;
    private String layout;

    public StageCreated(String workflowId, String stageId, String name, int wipLimit, String layout) {
        this.occurredOn = DateProvider.now();
        this.workflowId = workflowId;
        this.stageId = stageId;
        this.name = name;
        this.wipLimit = wipLimit;
        this.layout = layout;
    }

    @Override
    public Date getOccurredOn() {
        return occurredOn;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getStageId() {
        return stageId;
    }

    public String getName() {
        return name;
    }

    public int getWipLimit() {
        return wipLimit;
    }

    public String getLayout() {
        return layout;
    }
}
