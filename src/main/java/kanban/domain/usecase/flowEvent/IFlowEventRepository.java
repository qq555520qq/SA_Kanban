package kanban.domain.usecase.flowEvent;

import kanban.domain.model.FlowEvent;

import java.util.List;

public interface IFlowEventRepository {
    public void save(FlowEvent flowEvent);

    public List<FlowEvent> getAll();
}
