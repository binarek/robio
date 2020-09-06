package binarek.robio.registration.domain.competitor.event;

import binarek.robio.common.codegen.ValueTypeStyle;
import binarek.robio.common.domain.event.DomainEvent;
import binarek.robio.registration.domain.competitor.CompetitorId;
import binarek.robio.registration.domain.competitor.FirstName;
import binarek.robio.registration.domain.competitor.LastName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableCompetitorCreated.class)
public abstract class CompetitorCreated implements DomainEvent {

    CompetitorCreated() {
    }

    public static CompetitorCreated of(CompetitorId competitorId, FirstName firstName, LastName lastName,
                                       boolean isUnderage) {
        return ImmutableCompetitorCreated.builder()
                .occurredOn(DomainEvent.occurredNow())
                .competitorId(competitorId)
                .firstName(firstName)
                .lastName(lastName)
                .isUnderage(isUnderage)
                .build();
    }

    public abstract CompetitorId getCompetitorId();

    @Value.Redacted
    public abstract FirstName getFirstName();

    @Value.Redacted
    public abstract LastName getLastName();

    public abstract boolean isUnderage();
}
