package binarek.robio.auth.adapter.rest.api;

import binarek.robio.auth.AuthAppService;
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
@Tag(name = "authentication, authorization")
class AuthController {

    private final AuthAppService authAppService;
    private final AuthDtoMapper authDtoMapper;

    AuthController(AuthAppService authAppService, AuthDtoMapper authDtoMapper) {
        this.authAppService = authAppService;
        this.authDtoMapper = authDtoMapper;
    }

    @PostMapping("/login")
    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @Valid
    TokensDto login(Authentication authentication) {
        final var tokens = authAppService.generateTokens(authentication);
        return authDtoMapper.toTokensDto(tokens);
    }
}
