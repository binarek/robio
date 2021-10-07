package binarek.robio.auth;

import binarek.robio.auth.model.RefreshToken;
import binarek.robio.auth.model.RefreshTokenId;
import binarek.robio.auth.model.UserId;

import java.time.Instant;

public interface RefreshTokenWhitelistRepository {

    void add(RefreshToken refreshToken);

    boolean removeByTokenId(RefreshTokenId refreshTokenId);

    void removeAllByUserId(UserId userId);

    void removeByExpireAtBefore(Instant dateTime);
}
