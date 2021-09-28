package binarek.robio.ftl.adapter.persistence.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "robio.ftl.persistence")
@ConstructorBinding
class FtlPersistenceProperties {

    private final String schema;

    FtlPersistenceProperties(@DefaultValue("ftl") String schema) {
        this.schema = schema;
    }

    String getSchema() {
        return schema;
    }
}
