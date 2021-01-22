package binarek.robio.ftl.adapter.rest.api.dto;

import binarek.robio.util.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableCompetitionRulesDto.class)
@Schema(name = "CompetitionRules")
public interface CompetitionRulesDto {

    @Nullable
    @Min(1)
    Integer getRunsLimitPerRobot();
}
