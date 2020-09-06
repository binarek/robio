package binarek.robio.common.domain.event;

public interface DomainEventStore {

    void save(DomainEvent domainEvent);
}
