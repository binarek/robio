package binarek.robio.auth;

import binarek.robio.auth.configuration.AuthTokenProperties;
import binarek.robio.auth.exception.JwtValidationException;
import binarek.robio.auth.model.*;
import binarek.robio.auth.model.Token;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class TokenFactory {

    private final AuthTokenProperties tokenProperties;
    private final JwtService jwtService;

    TokenFactory(AuthTokenProperties tokenProperties,
                 JwtService jwtService) {
        this.tokenProperties = tokenProperties;
        this.jwtService = jwtService;
    }

    public TokensPair generateNewTokensForUser(Username username, UserRole userRole) {
        final var refreshToken = createRefreshToken(username);
        final var accessToken = createAccessToken(refreshToken.getClaims(), userRole);
        return TokensPair.of(refreshToken, accessToken);
    }

    public Token createValidTokenFromJwt(String jwt) {
        try {
            return AccessToken.of(jwt, jwtService.validateAndParseAccessJwtClaims(jwt));
        } catch (JwtValidationException e) {
            return RefreshToken.of(jwt, jwtService.validateAndParseRefreshJwtClaims(jwt));
        }
    }

    private RefreshToken createRefreshToken(Username username) {
        final var now = jwtService.getNow();
        final var claims = RefreshTokenClaims.builder()
                .tokenId(RefreshTokenId.of(UUID.randomUUID()))
                .username(username)
                .issuedAt(now)
                .expiredAt(resolveExpiredAtForRefreshToken(now))
                .build();
        return RefreshToken.of(jwtService.createRefreshJwt(claims), claims);
    }

    private AccessToken createAccessToken(RefreshTokenClaims refreshTokenClaims, UserRole userRole) {
        final var claims = AccessTokenClaims.builder()
                .username(refreshTokenClaims.getUsername())
                .issuedAt(refreshTokenClaims.getIssuedAt())
                .expiredAt(resolveExpiredAtForAccessToken(refreshTokenClaims.getIssuedAt()))
                .role(userRole)
                .build();
        return AccessToken.of(jwtService.createAccessJwt(claims), claims);
    }

    private Instant resolveExpiredAtForRefreshToken(Instant issuedAt) {
        return issuedAt.plus(tokenProperties.getRefresh().getValidityDuration());
    }

    private Instant resolveExpiredAtForAccessToken(Instant issuedAt) {
        return issuedAt.plus(tokenProperties.getAccess().getValidityDuration());
    }
}
