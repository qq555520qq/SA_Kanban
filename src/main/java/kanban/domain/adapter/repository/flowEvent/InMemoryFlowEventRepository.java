package kanban.domain.adapter.repository.flowEvent;

import kanban.domain.model.FlowEvent;
import kanban.domain.usecase.flowEvent.IFlowEventRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryFlowEventRepository implements IFlowEventRepository {

    List<FlowEvent> flowEvents = new ArrayList<>();

    @Override
    public void save(FlowEvent flowEvent) {
        flowEvents.add(flowEvent);
    }

    @Override
    public List<FlowEvent> getAll() {
        return flowEvents;
    }
}
