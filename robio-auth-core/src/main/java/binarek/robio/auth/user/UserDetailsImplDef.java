package binarek.robio.auth.user;

import binarek.robio.auth.user.view.UserView;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Value.Immutable
@ValueDefStyle
abstract class UserDetailsImplDef implements UserDetails {

    private static final String ROLE_PREFIX = "ROLE_";

    @Value.Parameter
    protected abstract UserView getUser();

    @Override
    @Value.Derived
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(ROLE_PREFIX + getUser().getRole()));
    }

    @Override
    public String getPassword() {
        return getUser().getHashedPassword().getValue();
    }

    @Override
    public String getUsername() {
        return getUser().getUsername().getValue();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
