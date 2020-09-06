package binarek.robio.common.domain.event;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class DomainEventStoreListener {

    private final DomainEventStore eventStore;

    public DomainEventStoreListener(DomainEventStore eventStore) {
        this.eventStore = eventStore;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    void handleDomainEvent(DomainEvent domainEvent) {
        eventStore.save(domainEvent);
    }
}
