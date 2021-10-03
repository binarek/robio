package binarek.robio.auth.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
abstract class RefreshTokenDef {

    @Value.Parameter
    public abstract String getJwt();

    @Value.Parameter
    public abstract RefreshTokenClaims getClaims();
}
