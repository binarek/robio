package binarek.robio.auth.model;

import binarek.robio.auth.view.RefreshTokenView;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
abstract class RefreshTokenDef implements RefreshTokenView {

    @Override
    @Value.Parameter
    public abstract String getJwt();

    @Value.Parameter
    public abstract RefreshTokenClaims getClaims();
}
