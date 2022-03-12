package binarek.robio.auth.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Value.Immutable
@ValueDefStyle
abstract class AccessTokenDef implements Token {

    @Override
    @Value.Parameter
    public abstract String getJwt();

    @Value.Parameter
    public abstract AccessTokenClaims getClaims();

    @Override
    public final UserId getUserId() {
        return getClaims().getUserId();
    }

    @Override
    public final Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(Authorities.getForRole(getClaims().getRole())));
    }
}
