package binarek.robio.auth;

import binarek.robio.auth.model.RefreshToken;
import binarek.robio.auth.model.RefreshTokenId;
import binarek.robio.auth.model.Username;

import java.time.Instant;

public interface RefreshTokenWhitelistRepository {

    void add(RefreshToken refreshToken);

    boolean removeByTokenId(RefreshTokenId refreshTokenId);

    void removeAllByUsername(Username username);

    void removeByExpireAtBefore(Instant dateTime);
}
