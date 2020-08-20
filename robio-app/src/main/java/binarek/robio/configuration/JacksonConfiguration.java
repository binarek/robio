package binarek.robio.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer objectMapperBuilderCustomizer() {
        return builder -> builder
                .modules(new ProblemModule(), new ConstraintViolationProblemModule())
                .serializationInclusion(JsonInclude.Include.NON_EMPTY);
    }
}
