package binarek.robio.auth.model;

import binarek.robio.auth.view.AccessTokenView;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Value.Immutable
@ValueDefStyle
abstract class AccessTokenDef implements AccessTokenView {

    @Override
    @Value.Parameter
    public abstract String getJwt();

    @Value.Parameter
    public abstract AccessTokenClaims getClaims();

    @Override
    public final UUID getSubject() {
        return getClaims().getSubject().getValue();
    }

    @Override
    public final Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(getClaims().getRole().getSpringRole()));
    }
}
