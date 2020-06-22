package kanban.domain.model.aggregate.workflow;

public class WipLimit {
    private int wip;

    public WipLimit(int wip) {
        this.wip = wip;
    }

    public int getWip() {
        return wip;
    }

    public void setWip(int wip) {
        this.wip = wip;
    }

    public int toInt() {
        return wip;
    }
}
