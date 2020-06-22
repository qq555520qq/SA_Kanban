package kanban.domain.usecase.handler.flowEvent;

import com.google.common.eventbus.Subscribe;
import kanban.domain.model.FlowEvent;
import kanban.domain.usecase.flowEvent.IFlowEventRepository;

public class FlowEventHandler {

    private IFlowEventRepository IFlowEventRepository;

    public FlowEventHandler(IFlowEventRepository IFlowEventRepository) {
        this.IFlowEventRepository = IFlowEventRepository;

    }

    @Subscribe
    public void handleEvent(FlowEvent flowEvent) {
        IFlowEventRepository.save(flowEvent);
    }
}
