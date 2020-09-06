package binarek.robio.registration.domain.competitor.event;

import binarek.robio.common.codegen.ValueTypeStyle;
import binarek.robio.common.domain.event.DomainEvent;
import binarek.robio.registration.domain.competitor.CompetitorId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableCompetitorApproved.class)
public abstract class CompetitorApproved implements DomainEvent {

    CompetitorApproved() {
    }

    public static CompetitorApproved byOwner(CompetitorId competitorId) {
        return initBuilder(competitorId)
                .byOwner(true)
                .byParent(false)
                .build();
    }

    public static CompetitorApproved byParent(CompetitorId competitorId) {
        return initBuilder(competitorId)
                .byOwner(false)
                .byParent(true)
                .build();
    }

    public abstract CompetitorId getCompetitorId();

    public abstract boolean byOwner();

    public abstract boolean byParent();

    private static ImmutableCompetitorApproved.Builder initBuilder(CompetitorId competitorId) {
        return ImmutableCompetitorApproved.builder()
                .occurredOn(DomainEvent.occurredNow())
                .competitorId(competitorId);
    }
}
