package binarek.robio.auth.model;

import binarek.robio.auth.view.AccessTokenView;
import binarek.robio.auth.view.RefreshTokenView;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
abstract class TokensPairDef {

    @Value.Parameter
    public abstract RefreshTokenView getRefreshToken();

    @Value.Parameter
    public abstract AccessTokenView getAccessToken();
}
