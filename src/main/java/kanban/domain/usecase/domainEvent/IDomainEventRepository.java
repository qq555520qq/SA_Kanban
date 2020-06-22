package kanban.domain.usecase.domainEvent;

import kanban.domain.model.DomainEvent;

import java.util.Collection;
import java.util.List;

public interface IDomainEventRepository {
    public void save(DomainEvent domainEvent);

    public List<DomainEvent> getAll();
}
