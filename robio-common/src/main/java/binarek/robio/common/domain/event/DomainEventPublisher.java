package binarek.robio.common.domain.event;

import binarek.robio.common.domain.AggregateRoot;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    public DomainEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishEvents(AggregateRoot aggregateRoot) {
        for (var event : aggregateRoot.getEventsToPublish()) {
            publisher.publishEvent(event);
        }
    }
}
