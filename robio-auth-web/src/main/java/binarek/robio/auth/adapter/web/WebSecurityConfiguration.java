package binarek.robio.auth.adapter.web;

import binarek.robio.auth.AuthAppService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthAppService authAppService;

    WebSecurityConfiguration(AuthAppService authAppService) {
        this.authAppService = authAppService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final var accessTokenFilter = new AccessTokenFilter(authAppService);
        // @formatter:off
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    .antMatchers("/auth/**").permitAll()
                    .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .anyRequest().authenticated()
            .and()
                .addFilterBefore(accessTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // @formatter:on
    }
}