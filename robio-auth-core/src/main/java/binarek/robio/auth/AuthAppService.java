package binarek.robio.auth;

import binarek.robio.auth.model.Token;
import binarek.robio.auth.model.TokensPair;

public interface AuthAppService {

    /**
     * Generates new access and refresh token from given principal.
     * Principal may be user details in case of first token pair or refresh token if generating next tokens.
     * It should be retrieved from current Spring Authentication object.
     *
     * @param authenticationPrincipal authentication principal - user details or refresh token
     * @return access token and refresh token
     */
    TokensPair generateTokens(Object authenticationPrincipal);

    /**
     * Validates given JWT and parse it to token object. It may be refresh or access token.
     *
     * @param jwt refresh or access JWT
     * @return refresh or access token
     */
    Token validateAndParseJwt(String jwt);
}
