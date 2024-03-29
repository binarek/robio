package binarek.robio.ftl.adapter.rest.api.dto;

import binarek.robio.util.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableRegisterRobotCommandDto.class)
@Schema(name = "RegisterRobotCommandDto")
public interface RegisterRobotCommandDto {

    @Nullable
    @Schema(required = true)
    @NotNull
    UUID getRobotId();

    @Nullable
    @Schema(required = true)
    @NotBlank
    @Size(min = 3)
    String getName();
}
