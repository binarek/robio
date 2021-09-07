package binarek.robio.ftl.adapter.rest.api.dto;

import binarek.robio.util.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableRobotPatchDto.class)
@Schema(name = "RobotPatch")
public interface RobotPatchDto {

    @Nullable
    @Schema(required = true)
    @NotNull
    Qualification getQualification();

    enum Qualification {
        QUALIFIED,
        DISQUALIFIED,
    }
}
