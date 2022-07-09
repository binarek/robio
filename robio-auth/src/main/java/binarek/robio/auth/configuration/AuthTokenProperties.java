package binarek.robio.auth.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;

@ConfigurationProperties(prefix = "robio.auth.core.token")
@ConstructorBinding
public class AuthTokenProperties {

    private final byte[] keySecret;
    @Nullable
    private final String issuer;
    private final RefreshProperties refresh;
    private final AccessProperties access;

    public AuthTokenProperties(@Nullable String keySecret,
                               @Nullable String issuer,
                               @DefaultValue RefreshProperties refresh,
                               @DefaultValue AccessProperties access) {
        this.keySecret = Objects.requireNonNull(keySecret, "Property robio.auth.core.token.key-secret is not defined!")
                .getBytes(StandardCharsets.UTF_8);
        this.issuer = issuer;
        this.refresh = refresh;
        this.access = access;
    }

    public byte[] getKeySecret() {
        return keySecret;
    }

    @Nullable
    public String getIssuer() {
        return issuer;
    }

    public RefreshProperties getRefresh() {
        return refresh;
    }

    public AccessProperties getAccess() {
        return access;
    }

    @ConstructorBinding
    public static class RefreshProperties {

        private final Duration validityDuration;

        public RefreshProperties(@DefaultValue("3d") Duration validityDuration) {
            this.validityDuration = validityDuration;
        }

        public Duration getValidityDuration() {
            return validityDuration;
        }
    }

    @ConstructorBinding
    public static class AccessProperties {

        private final Duration validityDuration;

        public AccessProperties(@DefaultValue("15m") Duration validityDuration) {
            this.validityDuration = validityDuration;
        }

        public Duration getValidityDuration() {
            return validityDuration;
        }
    }
}
