package binarek.robio.auth.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

import java.time.Instant;

@Value.Immutable
@ValueDefStyle
abstract class RefreshTokenClaimsDef {

    @Value.Parameter
    public abstract RefreshTokenId getTokenId();

    @Value.Parameter
    public abstract Username getSubject();

    @Value.Parameter
    public abstract Instant getIssuedAt();

    @Value.Parameter
    public abstract Instant getExpiredAt();

}
