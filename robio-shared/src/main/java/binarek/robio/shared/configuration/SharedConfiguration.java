package binarek.robio.shared.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SharedProperties.class)
class SharedConfiguration {
}
