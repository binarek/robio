package binarek.robio.registration.rest;

import binarek.robio.registration.domain.CompetitorId;
import binarek.robio.registration.domain.FirstName;
import binarek.robio.registration.domain.LastName;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.UUID;

@Configuration
public class RestConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new FirstNameConverter());
        registry.addConverter(new CompetitorIdConverter());
        registry.addConverter(new LastNameConverter());
    }

    private static class CompetitorIdConverter implements Converter<String, CompetitorId> {
        @Override
        public CompetitorId convert(String source) {
            return CompetitorId.of(UUID.fromString(source));
        }
    }

    private static class FirstNameConverter implements Converter<String, FirstName> {
        @Override
        public FirstName convert(String source) {
            return FirstName.of(source);
        }
    }

    private static class LastNameConverter implements Converter<String, LastName> {
        @Override
        public LastName convert(String source) {
            return LastName.of(source);
        }
    }
}