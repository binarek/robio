package binarek.robio.auth.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

import java.time.Instant;

@Value.Immutable
@ValueDefStyle
abstract class RefreshTokenClaimsDef {

    public abstract RefreshTokenId getTokenId();

    public abstract Username getUsername();

    public abstract Instant getIssuedAt();

    public abstract Instant getExpiredAt();

}
