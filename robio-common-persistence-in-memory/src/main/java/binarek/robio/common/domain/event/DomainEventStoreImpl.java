package binarek.robio.common.domain.event;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DomainEventStoreImpl implements DomainEventStore {

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    @Override
    public void save(DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
    }
}
