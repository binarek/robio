package binarek.robio.auth.adapter.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class LoginAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    LoginAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=\"Realm\"");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        Problem responseBody = Problem.builder()
                .with("timestamp", new Date())
                .withStatus(Status.UNAUTHORIZED)
                .with("error", Status.UNAUTHORIZED.getReasonPhrase())
                .with("path", request.getServletPath())
                .build();
        objectMapper.writeValue(response.getOutputStream(), responseBody);
    }
}
