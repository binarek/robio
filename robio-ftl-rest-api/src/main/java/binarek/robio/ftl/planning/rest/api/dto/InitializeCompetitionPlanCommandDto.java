package binarek.robio.ftl.planning.rest.api.dto;

import binarek.robio.common.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableInitializeCompetitionPlanCommandDto.class)
@Schema(name = "InitializeCompetitionPlanCommand")
public interface InitializeCompetitionPlanCommandDto {

    @Nullable
    @Schema(required = true)
    @NotNull
    UUID getCompetitionId();

    @Nullable
    @Min(1)
    Integer getRunsLimitPerRobot();
}
