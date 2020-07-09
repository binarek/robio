package binarek.robio.domain.team;

import binarek.robio.domain.person.PersonId;
import binarek.robio.util.codegen.BaseStyle;
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
