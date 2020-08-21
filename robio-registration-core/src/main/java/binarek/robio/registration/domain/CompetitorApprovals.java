package binarek.robio.registration.domain;

import binarek.robio.common.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableCompetitorApprovals.class)
public abstract class CompetitorApprovals {

    CompetitorApprovals() {
    }

    public abstract boolean isApprovedByOwner();

    public abstract boolean isApprovedByParent();

    static CompetitorApprovals newApprovals() {
        return ImmutableCompetitorApprovals.builder()
                .isApprovedByOwner(false)
                .isApprovedByParent(false)
                .build();
    }

    abstract CompetitorApprovals withIsApprovedByOwner(boolean isApprovedByOwner);

    abstract CompetitorApprovals withIsApprovedByParent(boolean isApprovedByParent);
}
