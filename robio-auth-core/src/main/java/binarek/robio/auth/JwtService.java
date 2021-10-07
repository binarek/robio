package binarek.robio.auth;

import binarek.robio.auth.configuration.AuthTokenProperties;
import binarek.robio.auth.exception.JwtValidationException;
import binarek.robio.auth.model.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private static final String ROLE_CLAIM = "role";
    private static final String SUBJECT_CLAIM = PublicClaims.SUBJECT;
    private static final String JWT_ID_CLAIM = PublicClaims.JWT_ID;

    @Nullable
    private final String issuer;
    private final Algorithm algorithm;
    private final JWTVerifier refreshJwtVerifier;
    private final JWTVerifier accessJwtVerifier;

    public JwtService(AuthTokenProperties tokenProperties) {
        this.issuer = tokenProperties.getIssuer();
        this.algorithm = Algorithm.HMAC256(tokenProperties.getKeySecret());
        this.refreshJwtVerifier = createRefreshJwtVerifier(tokenProperties);
        this.accessJwtVerifier = createAccessJwtVerifier(tokenProperties);
    }

    /**
     * Creates JWT for refresh token from given claims.
     *
     * @param claims claims
     * @return JWT
     */
    public String createRefreshJwt(RefreshTokenClaims claims) {
        return JWT.create()
                .withJWTId(claims.getTokenId().getValue().toString())
                .withSubject(claims.getUserId().getValue().toString())
                .withExpiresAt(Date.from(claims.getExpiredAt()))
                .withIssuer(issuer)
                .withIssuedAt(Date.from(claims.getIssuedAt()))
                .sign(algorithm);
    }

    /**
     * Creates JWT for access token from given claims.
     *
     * @param claims claims
     * @return JWT
     */
    public String createAccessJwt(AccessTokenClaims claims) {
        return JWT.create()
                .withSubject(claims.getUserId().getValue().toString())
                .withExpiresAt(Date.from(claims.getExpiredAt()))
                .withIssuer(issuer)
                .withIssuedAt(Date.from(claims.getIssuedAt()))
                .withClaim(ROLE_CLAIM, claims.getRole().name())
                .sign(algorithm);
    }

    /**
     * Validates and parses JWT of refresh token.
     *
     * @param jwt refresh token JWT
     * @return refresh token claims
     */
    public RefreshTokenClaims validateAndParseRefreshJwtClaims(String jwt) {
        final var decodedJwt = decodeJwt(jwt);
        validateDecodedRefreshJwt(decodedJwt);

        return RefreshTokenClaims.builder()
                .tokenId(getAndValidateJwtId(decodedJwt))
                .userId(getAndValidateSubject(decodedJwt))
                .issuedAt(decodedJwt.getIssuedAt().toInstant())
                .expiredAt(decodedJwt.getExpiresAt().toInstant())
                .build();
    }

    /**
     * Validates and parses JWT of access token.
     *
     * @param jwt access token JWT
     * @return access token claims
     */
    public AccessTokenClaims validateAndParseAccessJwtClaims(String jwt) {
        final var decodedJwt = decodeJwt(jwt);
        validateDecodedAccessJwt(decodedJwt);

        return AccessTokenClaims.builder()
                .userId(getAndValidateSubject(decodedJwt))
                .issuedAt(decodedJwt.getIssuedAt().toInstant())
                .expiredAt(decodedJwt.getExpiresAt().toInstant())
                .role(getAndValidateRole(decodedJwt))
                .build();
    }

    /**
     * Returns current instant that is compatible with the way of verifying JWT time claims (like expired at).
     *
     * @return current instant, compatible with JWT verification
     */
    public Instant getNow() {
        return new Date().toInstant();  // For compatibility with JWT library, which internally uses `new Date()`
    }

    private JWTVerifier createRefreshJwtVerifier(AuthTokenProperties tokenProperties) {
        return JWT.require(algorithm)
                .acceptLeeway(2)
                .withIssuer(tokenProperties.getIssuer())
                .withClaimPresence(JWT_ID_CLAIM)
                .withClaimPresence(SUBJECT_CLAIM)
                .build();
    }

    private JWTVerifier createAccessJwtVerifier(AuthTokenProperties tokenProperties) {
        return JWT.require(algorithm)
                .acceptLeeway(2)
                .withIssuer(tokenProperties.getIssuer())
                .withClaimPresence(SUBJECT_CLAIM)
                .withClaimPresence(ROLE_CLAIM)
                .build();
    }

    private static DecodedJWT decodeJwt(String jwt) {
        try {
            return JWT.decode(jwt);
        } catch (JWTDecodeException e) {
            throw new JwtValidationException("Cannot decode JWT " + jwt);
        }
    }

    private void validateDecodedRefreshJwt(DecodedJWT decodedJwt) {
        try {
            refreshJwtVerifier.verify(decodedJwt);
        } catch (JWTVerificationException e) {
            throw new JwtValidationException(e.getMessage());
        }
    }


    private void validateDecodedAccessJwt(DecodedJWT decodedJwt) {
        try {
            accessJwtVerifier.verify(decodedJwt);
        } catch (JWTVerificationException e) {
            throw new JwtValidationException(e.getMessage());
        }
    }

    private static RefreshTokenId getAndValidateJwtId(DecodedJWT decodedJwt) {
        final var id = decodedJwt.getId();
        UUID idUuid;
        try {
            idUuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new JwtValidationException("Token contains invalid JWT  id " + id);
        }
        return RefreshTokenId.of(idUuid);
    }

    private static UserId getAndValidateSubject(DecodedJWT decodedJwt) {
        final var subject = decodedJwt.getSubject();
        UUID subjectUuid;
        try {
            subjectUuid = UUID.fromString(subject);
        } catch (IllegalArgumentException e) {
            throw new JwtValidationException("Token contains invalid subject " + subject);
        }
        return UserId.of(subjectUuid);
    }

    private static UserRole getAndValidateRole(DecodedJWT decodedJwt) {
        final var role = decodedJwt.getClaim(ROLE_CLAIM).asString();
        if (!UserRole.isValidRole(role)) {
            throw new JwtValidationException("Token contains invalid role " + role);
        }
        return UserRole.valueOf(role);
    }
}
