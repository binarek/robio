package binarek.robio.ftl.adapter.rest.api.dto;

import binarek.robio.util.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableInitializeCompetitionCommandDto.class)
@Schema(name = "InitializeCompetitionCommand")
public interface InitializeCompetitionCommandDto {

    @Nullable
    @Schema(required = true)
    @NotNull
    UUID getCompetitionId();

    @Nullable
    @Valid
    CompetitionRulesDto getRules();
}
