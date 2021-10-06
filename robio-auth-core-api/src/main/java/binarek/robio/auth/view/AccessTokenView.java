package binarek.robio.auth.view;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface AccessTokenView {

    String getJwt();

    Object getSubject();

    Collection<? extends GrantedAuthority> getAuthorities();
}
