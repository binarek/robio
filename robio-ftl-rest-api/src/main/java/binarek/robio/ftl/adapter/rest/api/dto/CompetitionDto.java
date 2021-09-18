package binarek.robio.ftl.adapter.rest.api.dto;

import binarek.robio.ftl.model.CompetitionState;
import binarek.robio.util.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableCompetitionDto.class)
@Schema(name = "Competition")
public interface CompetitionDto {

    @Nullable
    @Schema(required = true)
    @NotNull
    UUID getCompetitionId();

    @Nullable
    List<CompetitionPlanRobotDto> getRobots();

    @Nullable
    @Valid
    CompetitionRulesDto getRules();

    @Nullable
    @NotNull
    CompetitionState getState();

    @Nullable
    @NotNull
    ZonedDateTime getStartDateTime();

    @Nullable
    ZonedDateTime getFinishDateTime();
}
