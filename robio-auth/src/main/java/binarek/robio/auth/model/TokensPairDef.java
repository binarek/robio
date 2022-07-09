package binarek.robio.auth.model;

import binarek.robio.util.codegen.ValueWithoutBuilderDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueWithoutBuilderDefStyle
abstract class TokensPairDef {

    @Value.Parameter
    public abstract RefreshToken getRefreshToken();

    @Value.Parameter
    public abstract AccessToken getAccessToken();
}
