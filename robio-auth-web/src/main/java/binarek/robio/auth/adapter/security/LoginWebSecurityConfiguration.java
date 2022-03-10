package binarek.robio.auth.adapter.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Order(-1)
@Configuration
@EnableWebSecurity
public class LoginWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final LoginAuthenticationEntryPoint loginAuthenticationEntryPoint;

    LoginWebSecurityConfiguration(LoginAuthenticationEntryPoint loginAuthenticationEntryPoint) {
        this.loginAuthenticationEntryPoint = loginAuthenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .requestMatchers().antMatchers("/auth/login")
            .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests().anyRequest().authenticated()
            .and()
                .httpBasic().authenticationEntryPoint(loginAuthenticationEntryPoint);
        // @formatter:on
    }
}
