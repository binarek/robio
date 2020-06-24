package binarek.robio.core.domain.team;

import binarek.robio.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableTeamMember.class)
public interface TeamMember {

    @Value.Redacted
    FirstName getFirstName();

    @Value.Redacted
    LastName getLastName();

    Role getRole();

    enum Role {
        CONSTRUCTOR,
        OTHER,
    }
}
