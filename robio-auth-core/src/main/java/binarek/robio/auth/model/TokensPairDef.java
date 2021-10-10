package binarek.robio.auth.model;

import binarek.robio.auth.view.TokensPairView;
import binarek.robio.util.codegen.ValueWithoutBuilderDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueWithoutBuilderDefStyle
abstract class TokensPairDef implements TokensPairView {

    @Override
    @Value.Parameter
    public abstract RefreshToken getRefreshToken();

    @Override
    @Value.Parameter
    public abstract AccessToken getAccessToken();
}
