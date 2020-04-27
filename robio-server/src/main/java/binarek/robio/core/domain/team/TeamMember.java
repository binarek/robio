package binarek.robio.core.domain.team;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@Value.Style(redactedMask = "***")
@JsonDeserialize(as = ImmutableTeamMember.class)
public interface TeamMember {

    @Value.Redacted
    String getFirstName();

    @Value.Redacted
    String getLastName();

    Role getRole();

    enum Role {
        CONSTRUCTOR,
        OTHER
    }
}
