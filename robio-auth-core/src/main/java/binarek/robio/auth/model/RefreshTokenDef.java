package binarek.robio.auth.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Value.Immutable
@ValueDefStyle
abstract class RefreshTokenDef implements Token {

    @Override
    @Value.Parameter
    public abstract String getJwt();

    @Value.Parameter
    public abstract RefreshTokenClaims getClaims();

    @Override
    public final Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of();
    }
}
