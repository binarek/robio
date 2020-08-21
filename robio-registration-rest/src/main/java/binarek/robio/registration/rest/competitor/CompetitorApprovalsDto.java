package binarek.robio.registration.rest.competitor;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;

@Schema(name = "CompetitorApprovals")
@Value.Immutable
@JsonDeserialize(as = ImmutableCompetitorApprovalsDto.class)
public interface CompetitorApprovalsDto {

    Boolean getIsApprovedByOwner();

    Boolean getIsApprovedByParent();
}
