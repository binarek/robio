package binarek.robio.auth;

import binarek.robio.auth.configuration.AuthTokenProperties;
import binarek.robio.auth.exception.JwtValidationException;
import binarek.robio.auth.model.AccessTokenClaims;
import binarek.robio.auth.model.RefreshTokenClaims;
import binarek.robio.auth.model.UserRole;
import binarek.robio.auth.model.Username;
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

@Service
public class JwtService {

    private static final String ROLE_CLAIM = "role";
    private static final String SUBJECT_CLAIM = PublicClaims.SUBJECT;

    @Nullable
    private final String issuer;
    private final Algorithm algorithm;
    private final JWTVerifier accessJwtVerifier;

    public JwtService(AuthTokenProperties tokenProperties) {
        this.issuer = tokenProperties.getIssuer();
        this.algorithm = Algorithm.HMAC256(tokenProperties.getKeySecret());
        this.accessJwtVerifier = createAccessJwtVerifier(tokenProperties);
    }

    public String createRefreshJwt(RefreshTokenClaims claims) {
        return JWT.create()
                .withJWTId(claims.getTokenId().getValue().toString())
                .withSubject(claims.getSubject().getValue())
                .withExpiresAt(Date.from(claims.getExpiredAt()))
                .withIssuer(issuer)
                .withIssuedAt(Date.from(claims.getIssuedAt()))
                .sign(algorithm);
    }

    public String createAccessJwt(AccessTokenClaims claims) {
        return JWT.create()
                .withSubject(claims.getSubject().getValue())
                .withExpiresAt(Date.from(claims.getExpiredAt()))
                .withIssuer(issuer)
                .withIssuedAt(Date.from(claims.getIssuedAt()))
                .withClaim(ROLE_CLAIM, claims.getRole().name())
                .sign(algorithm);
    }

    public AccessTokenClaims validateAndParseAccessJwtClaims(String jwt) {
        final var decodedJwt = decodeJwt(jwt);
        validateDecodedAccessJwt(decodedJwt);

        return AccessTokenClaims.builder()
                .subject(getAndValidateSubject(decodedJwt))
                .issuedAt(decodedJwt.getIssuedAt().toInstant())
                .expiredAt(decodedJwt.getExpiresAt().toInstant())
                .role(getAndValidateRole(decodedJwt))
                .build();
    }

    public Instant getNow() {
        return new Date().toInstant();  // For compatibility with JWT library, which internally uses `new Date()`
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

    private void validateDecodedAccessJwt(DecodedJWT decodedJwt) {
        try {
            accessJwtVerifier.verify(decodedJwt);
        } catch (JWTVerificationException e) {
            throw new JwtValidationException(e.getMessage());
        }
    }

    private static Username getAndValidateSubject(DecodedJWT decodedJwt) {
        final var subject = decodedJwt.getSubject();
        if (!Username.isValidUsername(subject)) {
            throw new JwtValidationException("Token contains invalid subject " + subject);
        }
        return Username.of(subject);
    }

    private static UserRole getAndValidateRole(DecodedJWT decodedJwt) {
        final var role = decodedJwt.getClaim(ROLE_CLAIM).asString();
        if (!UserRole.isValidRole(role)) {
            throw new JwtValidationException("Token contains invalid role " + role);
        }
        return UserRole.valueOf(role);
    }
}
