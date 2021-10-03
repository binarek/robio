package binarek.robio.auth.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
abstract class TokensPairDef {

    @Value.Parameter
    public abstract RefreshToken getRefreshToken();

    @Value.Parameter
    public abstract AccessToken getAccessToken();
}
