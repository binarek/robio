package binarek.robio.auth.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

import java.time.Instant;

@Value.Immutable
@ValueDefStyle
abstract class AccessTokenClaimsDef {

    @Value.Parameter
    public abstract UserId getSubject();

    @Value.Parameter
    public abstract Instant getIssuedAt();

    @Value.Parameter
    public abstract Instant getExpiredAt();

    @Value.Parameter
    public abstract UserRole getRole();
}
