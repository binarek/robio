package binarek.robio.auth;

import binarek.robio.auth.configuration.AuthTokenProperties;
import binarek.robio.auth.model.*;
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

    public TokensPair generateNewTokensForUser(User user) {
        final var refreshToken = createRefreshToken(user);
        final var accessToken = createAccessToken(refreshToken.getClaims(), user);
        return TokensPair.of(refreshToken, accessToken);
    }

    public AccessToken createValidAccessTokenFromJwt(String jwt) {
        final var claims = jwtService.validateAndParseAccessJwtClaims(jwt);
        return AccessToken.of(jwt, claims);
    }

    private RefreshToken createRefreshToken(User user) {
        final var now = jwtService.getNow();
        final var claims = RefreshTokenClaims.builder()
                .tokenId(RefreshTokenId.of(UUID.randomUUID()))
                .subject(user.getUserId())
                .issuedAt(now)
                .expiredAt(resolveExpiredAtForRefreshToken(now))
                .build();
        return RefreshToken.of(jwtService.createRefreshJwt(claims), claims);
    }

    private AccessToken createAccessToken(RefreshTokenClaims refreshTokenClaims, User user) {
        final var claims = AccessTokenClaims.builder()
                .subject(refreshTokenClaims.getSubject())
                .issuedAt(refreshTokenClaims.getIssuedAt())
                .expiredAt(resolveExpiredAtForAccessToken(refreshTokenClaims.getIssuedAt()))
                .role(user.getRole())
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
