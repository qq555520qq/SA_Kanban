package kanban.domain.usecase.card.cycleTime;

import kanban.domain.model.FlowEvent;
import kanban.domain.model.common.DateProvider;

import java.util.Date;


public class FlowEventPair {
    private CycleTimeInStage cycleTimeInStage;
    public FlowEventPair(FlowEvent committed){
        String stageId = committed.getStageId();
        Date occurredOnOfCommitted = committed.getOccurredOn();
        long diff = (DateProvider.now().getTime() - occurredOnOfCommitted.getTime()) / 1000;
        cycleTimeInStage = new CycleTimeInStage(stageId, diff);
    }
    public FlowEventPair(FlowEvent committed, FlowEvent uncommitted) {

        String stageId = committed.getStageId();
        Date occurredOnOfCommitted = committed.getOccurredOn();
        Date occurredOnOfUncommitted = uncommitted.getOccurredOn();

        long diff = (occurredOnOfUncommitted.getTime() - occurredOnOfCommitted.getTime()) / 1000;

        cycleTimeInStage = new CycleTimeInStage(stageId, diff);
    }

    public CycleTimeInStage getCycleTimeInStage() {
        return cycleTimeInStage;
    }
}
