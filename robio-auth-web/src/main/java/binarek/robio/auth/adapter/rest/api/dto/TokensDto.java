package binarek.robio.auth.adapter.rest.api.dto;

import binarek.robio.util.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableTokensDto.class)
@Schema(name = "Tokens")
public interface TokensDto {

    @Nullable
    @Schema(required = true)
    @NotBlank
    String getRefreshToken();

    @Nullable
    @Schema(required = true)
    @NotBlank
    String getAccessToken();
}
