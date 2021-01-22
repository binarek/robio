package binarek.robio.ftl.adapter.persistence.configuration;

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
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
@AutoConfigureAfter({DataSourceAutoConfiguration.class, TransactionAutoConfiguration.class})
class FtlJooqConfiguration {

    @Bean
    @ConditionalOnMissingBean(ConnectionProvider.class)
    public DataSourceConnectionProvider connectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean(name = FtlBeanNames.DSL_CONTEXT)
    public DSLContext ftlDSLContext(ConnectionProvider connectionProvider) {
        return configuration(connectionProvider).dsl();
    }

    private DefaultConfiguration configuration(ConnectionProvider connectionProvider) {
        DefaultConfiguration config = new DefaultConfiguration();
        config.set(connectionProvider);
        config.set(SQLDialect.POSTGRES);
        config.set(new Settings()
                .withRenderMapping(new RenderMapping().withSchemata(new MappedSchema().withInput("").withOutput("ftl")))
                .withRenderSchema(true)
                .withRenderQuotedNames(RenderQuotedNames.NEVER)
                .withExecuteWithOptimisticLocking(true));
        config.set(new DefaultExecuteListenerProvider(new JooqExceptionTranslator()));
        return config;
    }
}
