package binarek.robio.core.domain.team;

import binarek.robio.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableTeamMember.class)
public abstract class TeamMember {

    TeamMember() {
    }

    public abstract UUID getCompetitorId();

    public abstract Role getRole();

    public enum Role {
        CONSTRUCTOR,
        OTHER,
    }
}
