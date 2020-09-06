package binarek.robio.registration.domain.competitor.event;

import binarek.robio.common.codegen.ValueTypeStyle;
import binarek.robio.common.domain.event.DomainEvent;
import binarek.robio.registration.domain.competitor.CompetitorId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableCompetitorDisapproved.class)
public abstract class CompetitorDisapproved implements DomainEvent {

    CompetitorDisapproved() {
    }

    public static CompetitorDisapproved byOwner(CompetitorId competitorId) {
        return builder(competitorId)
                .byOwner(true)
                .byParent(false)
                .build();
    }

    public static CompetitorDisapproved byParent(CompetitorId competitorId) {
        return builder(competitorId)
                .byOwner(false)
                .byParent(true)
                .build();
    }

    public abstract CompetitorId getCompetitorId();

    public abstract boolean byOwner();

    public abstract boolean byParent();

    private static ImmutableCompetitorDisapproved.Builder builder(CompetitorId competitorId) {
        return ImmutableCompetitorDisapproved.builder()
                .occurredOn(DomainEvent.occurredNow())
                .competitorId(competitorId);
    }

}
