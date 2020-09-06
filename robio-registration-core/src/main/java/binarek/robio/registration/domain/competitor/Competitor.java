package binarek.robio.registration.domain.competitor;

import binarek.robio.common.codegen.BaseStyle;
import binarek.robio.common.domain.AggregateRoot;
import binarek.robio.registration.domain.competitor.event.CompetitorApproved;
import binarek.robio.registration.domain.competitor.event.CompetitorCreated;
import binarek.robio.registration.domain.competitor.event.CompetitorDisapproved;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableCompetitor.class)
public abstract class Competitor implements AggregateRoot {

    Competitor() {
    }

    public abstract CompetitorId getId();

    @Nullable
    public abstract Long getVersion();

    @Value.Redacted
    public abstract FirstName getFirstName();

    @Value.Redacted
    public abstract LastName getLastName();

    public abstract boolean isUnderage();

    public abstract CompetitorApprovals getApprovals();

    @JsonProperty("isActive")
    public final boolean isActive() {
        return getApprovals().isApprovedByOwner()
                && (getApprovals().isApprovedByParent() || !isUnderage());
    }

    public static Competitor newCompetitor(CompetitorId id, FirstName firstName, LastName lastName, boolean isUnderage) {
        return builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .isUnderage(isUnderage)
                .approvals(CompetitorApprovals.newApprovals())
                .addEventsToPublish(CompetitorCreated.of(id, firstName, lastName, isUnderage))
                .build();
    }

    public final Competitor approveByOwner() {
        return toBuilder()
                .approvals(getApprovals().withIsApprovedByOwner(true))
                .addEventsToPublish(CompetitorApproved.byOwner(getId()))
                .build();
    }

    public final Competitor disapproveByOwner() {
        return toBuilder()
                .approvals(getApprovals().withIsApprovedByOwner(false))
                .addEventsToPublish(CompetitorDisapproved.byOwner(getId()))
                .build();
    }

    public final Competitor approveByParent() {
        return toBuilder()
                .approvals(getApprovals().withIsApprovedByParent(true))
                .addEventsToPublish(CompetitorApproved.byParent(getId()))
                .build();
    }

    public final Competitor disapproveByParent() {
        return toBuilder()
                .approvals(getApprovals().withIsApprovedByParent(false))
                .addEventsToPublish(CompetitorDisapproved.byParent(getId()))
                .build();
    }

    private static ImmutableCompetitor.Builder builder() {
        return ImmutableCompetitor.builder();
    }

    private ImmutableCompetitor.Builder toBuilder() {
        return ImmutableCompetitor.builder().from(this);
    }
}
