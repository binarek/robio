package binarek.robio.auth;

import binarek.robio.auth.model.Token;
import binarek.robio.auth.model.TokensPair;
import org.springframework.security.authentication.BadCredentialsException;

public interface AuthAppService {

    /**
     * Generates new access and refresh token.
     *
     * @return access token and refresh token
     */
    TokensPair generateTokens();

    /**
     * Validates given JWT and parse it to token object. It may be refresh or access token.
     *
     * @param jwt refresh or access JWT
     * @return refresh or access token
     */
    Token parseJwtToValidToken(String jwt);

    /**
     * Checks if given token credentials are valid.
     * Note that method is not idempotent - it can change state of credentials.
     *
     * @param credentials token credentials
     * @throws BadCredentialsException if credentials are invalid
     */
    void validateTokenCredentials(Token credentials);
}
