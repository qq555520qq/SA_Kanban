package kanban.domain.usecase.handler.domainEvent;

import com.google.common.eventbus.Subscribe;
import kanban.domain.model.DomainEvent;
import kanban.domain.usecase.domainEvent.IDomainEventRepository;

public class DomainEventHandler {

    private IDomainEventRepository domainEventRepository;

    public DomainEventHandler(IDomainEventRepository domainEventRepository) {
        this.domainEventRepository = domainEventRepository;

    }

    @Subscribe
    public void handleEvent(DomainEvent domainEvent) {
        domainEventRepository.save(domainEvent);
    }
}
