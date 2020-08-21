package binarek.robio.registration.rest.competitor;

import binarek.robio.registration.domain.CompetitorId;
import binarek.robio.registration.domain.FirstName;
import binarek.robio.registration.domain.LastName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Schema(name = "Competitor")
@Value.Immutable
@JsonDeserialize(as = ImmutableCompetitorDto.class)
public interface CompetitorDto {

    @Nullable
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    CompetitorId getId();

    @Nullable
    Long getVersion();

    @Schema(required = true)
    FirstName getFirstName();

    @Schema(required = true)
    LastName getLastName();

    @Nullable
    Boolean getIsUnderage();

    @Nullable
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    CompetitorApprovalsDto getApprovals();

    @Nullable
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    Boolean getIsActive();
}
