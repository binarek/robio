package binarek.robio.ftl.adapter.rest.api.dto;

import binarek.robio.util.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.PositiveOrZero;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableRunPatchDto.class)
@Schema(name = "RunPatch")
public interface RunPatchDto {

    @Nullable
    Judgement getJudgement();

    @Nullable
    @PositiveOrZero
    Long getTime();

    enum Judgement {
        PASSED,
        FAILED,
    }
}
