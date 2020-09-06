package binarek.robio.common.domain;

import binarek.robio.common.domain.event.DomainEvent;

import java.util.List;

public interface AggregateRoot {

    List<DomainEvent> getEventsToPublish();
}
