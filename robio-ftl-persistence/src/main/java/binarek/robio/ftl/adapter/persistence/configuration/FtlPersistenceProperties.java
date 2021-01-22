package binarek.robio.ftl.adapter.persistence.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "robio.ftl.persistence")
class FtlPersistenceProperties {

    private String schema = "ftl";

    String getSchema() {
        return schema;
    }

    void setSchema(String schema) {
        this.schema = schema;
    }
}
