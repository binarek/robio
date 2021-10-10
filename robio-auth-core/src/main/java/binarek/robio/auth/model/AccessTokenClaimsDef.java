package binarek.robio.auth.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

import java.time.Instant;

@Value.Immutable
@ValueDefStyle
abstract class AccessTokenClaimsDef {

    public abstract UserId getUserId();

    public abstract Instant getIssuedAt();

    public abstract Instant getExpiredAt();

    public abstract UserRole getRole();
}
