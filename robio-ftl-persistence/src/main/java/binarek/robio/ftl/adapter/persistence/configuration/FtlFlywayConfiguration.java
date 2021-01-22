package binarek.robio.ftl.adapter.persistence.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "ftl.flyway", name = "enabled", matchIfMissing = true)
class FtlFlywayConfiguration {

    @Bean
    Flyway ftlFlyway(ResourceLoader resourceLoader, DataSource dataSource) {
        return Flyway.configure(resourceLoader.getClassLoader())
                .dataSource(dataSource)
                .schemas("ftl")
                .sqlMigrationPrefix("FTL_")
                .load();
    }

    @Bean
    FlywayMigrationInitializer ftlFlywayMigrationInitializer(@Qualifier("ftlFlyway") Flyway ftlFlyway) {
        return new FlywayMigrationInitializer(ftlFlyway);
    }
}
