package binarek.robio.core.domain.team;

import binarek.robio.codegen.BaseStyle;
import binarek.robio.user.domain.person.PersonId;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableTeamMember.class)
public abstract class TeamMember {

    TeamMember() {
    }

    public abstract PersonId getCompetitorId();

    public abstract Role getRole();

    public enum Role {
        CONSTRUCTOR,
        OTHER,
    }
}
