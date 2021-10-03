package binarek.robio.auth;

import binarek.robio.auth.model.User;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Value.Immutable
@ValueDefStyle
abstract class UserDetailsImplDef implements UserDetails, UserHolder {

    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    @Value.Parameter
    public abstract User getUser();

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
