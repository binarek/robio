package binarek.robio.ftl.domain.execution.model;

import binarek.robio.common.codegen.ValueTypeStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.UUID;

import static binarek.robio.ftl.domain.execution.model.FtlParticipationDetails.QualificationStatus.DISQUALIFIED;

@Value.Immutable
@ValueTypeStyle
@JsonDeserialize(as = ImmutableFtlParticipationDetails.class)
public abstract class FtlParticipationDetails {

    @Value.Parameter
    public abstract UUID robotId();

    @Value.Parameter
    public abstract QualificationStatus qualificationStatus();

    public final FtlParticipationDetails disqualify() {
        return newFtlParticipationDetails(robotId(), DISQUALIFIED);
    }

    public static FtlParticipationDetails newFtlParticipationDetails(UUID robotId,
                                                                     QualificationStatus qualificationStatus) {
        return ImmutableFtlParticipationDetails.ofValue(robotId, qualificationStatus);
    }

    public enum QualificationStatus {
        QUALIFIED,
        DISQUALIFIED,
    }
}
