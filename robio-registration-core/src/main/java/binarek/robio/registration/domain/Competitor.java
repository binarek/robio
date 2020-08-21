package binarek.robio.registration.domain;

import binarek.robio.common.codegen.BaseStyle;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableCompetitor.class)
public abstract class Competitor {

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
        return ImmutableCompetitor.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .isUnderage(isUnderage)
                .approvals(CompetitorApprovals.newApprovals())
                .build();
    }

    public final Competitor approveByOwner() {
        return withApprovals(getApprovals().withIsApprovedByOwner(true));
    }

    public final Competitor disapproveByOwner() {
        return withApprovals(getApprovals().withIsApprovedByOwner(false));
    }

    public final Competitor approveByParent() {
        return withApprovals(getApprovals().withIsApprovedByParent(true));
    }

    public final Competitor disapproveByParent() {
        return withApprovals(getApprovals().withIsApprovedByParent(false));
    }

    abstract Competitor withApprovals(CompetitorApprovals approvals);
}
