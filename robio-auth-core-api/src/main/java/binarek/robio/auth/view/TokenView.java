package binarek.robio.auth.view;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface TokenView {

    String getJwt();

    Collection<? extends GrantedAuthority> getAuthorities();
}
