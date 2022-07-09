package binarek.robio.auth.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface Token {

    String getJwt();

    Username getUsername();

    Collection<? extends GrantedAuthority> getAuthorities();
}
