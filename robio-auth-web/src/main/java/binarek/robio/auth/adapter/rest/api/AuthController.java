package binarek.robio.auth.adapter.rest.api;

import binarek.robio.auth.TokenService;
import binarek.robio.auth.UserHolder;
import binarek.robio.auth.adapter.rest.api.dto.ImmutableTokensDto;
import binarek.robio.auth.adapter.rest.api.dto.TokensDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "authentication")
class AuthController {

    private final TokenService tokenService;

    AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @Valid
    TokensDto login(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserHolder) {
            final var user = ((UserHolder) authentication.getPrincipal()).getUser();
            final var tokens = tokenService.createTokensPair(user);
            return ImmutableTokensDto.builder()
                    .accessToken(tokens.getAccessToken().getJwt())
                    .refreshToken(tokens.getRefreshToken().getJwt())
                    .build();
        } else {
            throw new IllegalStateException("Cannot resolve user for principal class " + authentication.getPrincipal().getClass());
        }
    }
}
