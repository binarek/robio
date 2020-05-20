package binarek.robio.core.domain.team;

import binarek.robio.codegen.BaseStyle;
import binarek.robio.common.domain.value.PersonFirstName;
import binarek.robio.common.domain.value.PersonLastName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableTeamMember.class)
public interface TeamMember {

    @Value.Redacted
    PersonFirstName getFirstName();

    @Value.Redacted
    PersonLastName getLastName();

    Role getRole();

    enum Role {
        CONSTRUCTOR,
        OTHER,
    }
}
