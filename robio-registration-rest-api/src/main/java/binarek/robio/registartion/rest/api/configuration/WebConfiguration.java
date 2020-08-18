package binarek.robio.registartion.rest.api.configuration;

import binarek.robio.registration.domain.person.PersonId;
import binarek.robio.registration.domain.robot.RobotId;
import binarek.robio.registration.domain.team.TeamId;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.UUID;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new RobotIdConverter());
        registry.addConverter(new TeamIdConverter());
        registry.addConverter(new PersonIdConverter());
    }


    private static class RobotIdConverter implements Converter<String, RobotId> {
        @Override
        public RobotId convert(String source) {
            return RobotId.of(UUID.fromString(source));
        }
    }

    private static class TeamIdConverter implements Converter<String, TeamId> {
        @Override
        public TeamId convert(String source) {
            return TeamId.of(UUID.fromString(source));
        }
    }

    private static class PersonIdConverter implements Converter<String, PersonId> {
        @Override
        public PersonId convert(String source) {
            return PersonId.of(UUID.fromString(source));
        }
    }
}
