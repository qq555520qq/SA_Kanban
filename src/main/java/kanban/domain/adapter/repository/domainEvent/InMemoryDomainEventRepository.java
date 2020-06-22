package kanban.domain.adapter.repository.domainEvent;

import kanban.domain.model.DomainEvent;
import kanban.domain.usecase.domainEvent.IDomainEventRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDomainEventRepository implements IDomainEventRepository {

    List<DomainEvent> domainEvents;

    public InMemoryDomainEventRepository() {
        this.domainEvents = new ArrayList<>();
    }

    @Override
    public void save(DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
    }

    @Override
    public List<DomainEvent> getAll() {
        return domainEvents;
    }
}
