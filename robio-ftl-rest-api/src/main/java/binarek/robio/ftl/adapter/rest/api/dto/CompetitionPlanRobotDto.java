package binarek.robio.ftl.adapter.rest.api.dto;

import binarek.robio.util.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableCompetitionPlanRobotDto.class)
@Schema(name = "CompetitionPlanRobot")
public interface CompetitionPlanRobotDto {

    UUID getRobotId();
}
