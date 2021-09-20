package binarek.robio.ftl.adapter.rest.api.dto;

import binarek.robio.util.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableRunDto.class)
@Schema(name = "Run")
public interface RunDto {

    @Nullable
    @Schema(required = true)
    @NotNull
    UUID getCompetitionId();

    @Nullable
    @Schema(required = true)
    @NotNull
    UUID getRobotId();

    @Nullable
    @Schema(required = true)
    @NotNull
    Integer getNumber();

    @Nullable
    @Schema(required = true)
    @NotNull
    String getJudgement();

    @Nullable
    @Schema(required = true)
    @NotNull
    @PositiveOrZero
    Long getTime();
}
