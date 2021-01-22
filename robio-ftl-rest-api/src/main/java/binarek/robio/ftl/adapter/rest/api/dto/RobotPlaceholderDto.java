package binarek.robio.ftl.adapter.rest.api.dto;

import binarek.robio.util.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableRobotPlaceholderDto.class)
@Schema(name = "RobotPlaceholder")
public interface RobotPlaceholderDto {

    @Nullable
    @Schema(required = true)
    @NotNull
    UUID getTeamId();

    @Nullable
    UUID getRobotId();
}
