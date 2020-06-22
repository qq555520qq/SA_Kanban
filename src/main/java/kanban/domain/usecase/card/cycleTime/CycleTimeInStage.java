package kanban.domain.usecase.card.cycleTime;

public class CycleTimeInStage {
    private String stageId;
    private long diff;

    public CycleTimeInStage(String stageId, long diff) {
        this.stageId = stageId;
        this.diff = diff;
    }

    public String getStageId() {
        return stageId;
    }

    public long getDiff() {
        return diff;
    }
}
