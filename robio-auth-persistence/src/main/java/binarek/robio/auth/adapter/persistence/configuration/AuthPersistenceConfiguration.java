package binarek.robio.auth.adapter.persistence.configuration;

import org.flywaydb.core.Flyway;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(AuthPersistenceProperties.class)
@AutoConfigureAfter({DataSourceAutoConfiguration.class, TransactionAutoConfiguration.class})
class AuthPersistenceConfiguration {

    private static final String MIGRATION_FILE_PREFIX = "AUTH_";

    @Bean
    @ConditionalOnMissingBean(ConnectionProvider.class)
    DataSourceConnectionProvider connectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    Flyway authFlyway(ResourceLoader resourceLoader, DataSource dataSource, AuthPersistenceProperties properties) {
        return Flyway.configure(resourceLoader.getClassLoader())
                .dataSource(dataSource)
                .schemas(properties.getSchema())
                .sqlMigrationPrefix(MIGRATION_FILE_PREFIX)
                .load();
    }

    @Bean
    @ConditionalOnProperty(name = "robio.auth.persistence.flyway.enabled")
    FlywayMigrationInitializer authFlywayMigrationInitializer(@Qualifier("authFlyway") Flyway ftlFlyway) {
        return new FlywayMigrationInitializer(ftlFlyway);
    }

    @Bean(name = AuthBeanNames.DSL_CONTEXT)
    DSLContext authDSLContext(ConnectionProvider connectionProvider, AuthPersistenceProperties properties) {
        var config = new DefaultConfiguration();
        config.set(connectionProvider);
        config.set(SQLDialect.POSTGRES);
        config.set(new Settings()
                .withRenderMapping(new RenderMapping()
                        .withSchemata(new MappedSchema().withInput("").withOutput(properties.getSchema())))
                .withRenderSchema(true)
                .withRenderQuotedNames(RenderQuotedNames.NEVER)
                .withExecuteWithOptimisticLocking(true));
        config.set(new DefaultExecuteListenerProvider(new JooqExceptionTranslator()));
        return config.dsl();
    }
}
