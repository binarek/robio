package binarek.robio.auth.adapter.persistence.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "robio.auth.persistence")
@ConstructorBinding
class AuthPersistenceProperties {

    private final String schema;

    AuthPersistenceProperties(@DefaultValue("auth") String schema) {
        this.schema = schema;
    }

    String getSchema() {
        return schema;
    }
}
