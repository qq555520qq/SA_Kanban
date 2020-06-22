package kanban.domain.adapter.presenter.card.cycleTime;

import kanban.domain.usecase.card.cycleTime.CalculateCycleTimeOutput;
import kanban.domain.usecase.card.cycleTime.CycleTime;

public class CalculateCycleTimePresenter implements CalculateCycleTimeOutput {
    private CycleTime cycleTime;

    @Override
    public void setCycleTime(CycleTime cycleTime) {
        this.cycleTime = cycleTime;
    }

    @Override
    public CycleTime getCycleTime() {
        return cycleTime;
    }
}
