package binarek.robio.ftl.adapter.rest.api.dto;

import binarek.robio.util.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableAddRunCommandDto.class)
@Schema(name = "AddRunCommand")
public interface AddRunCommandDto {

    @Nullable
    @Schema(required = true)
    @NotNull
    Judgement getJudgement();

    @Nullable
    @Schema(required = true)
    @NotNull
    @PositiveOrZero
    Long getTime();

    enum Judgement {
        PASSED,
        FAILED,
    }
}
