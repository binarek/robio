package binarek.robio.shared.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.ZoneId;

@ConfigurationProperties(prefix = "robio.shared")
@ConstructorBinding
public class SharedProperties {

    private final ZoneId timezone;

    SharedProperties(@DefaultValue("Europe/Warsaw") ZoneId timezone) {
        this.timezone = timezone;
    }

    public ZoneId getTimezone() {
        return timezone;
    }
}
