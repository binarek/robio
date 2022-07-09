package binarek.robio.ftl.adapter.rest.api.dto;

import binarek.robio.util.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableScoreboardDto.class)
@Schema(name = "Scoreboard")
public interface ScoreboardDto {

    List<ScoreDto> getScores();
}
