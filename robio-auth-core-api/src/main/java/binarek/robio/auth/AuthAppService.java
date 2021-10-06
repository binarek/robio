package binarek.robio.auth;

import binarek.robio.auth.model.TokensPair;
import binarek.robio.auth.view.AccessTokenView;
import org.springframework.security.core.Authentication;

public interface AuthAppService {

    TokensPair generateTokens(Authentication authentication);

    AccessTokenView validateAndParseAccessJwt(String jwt);
}
