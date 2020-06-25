package binarek.robio.core.domain.team;

import binarek.robio.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableTeamMember.class)
public interface TeamMember {

    UUID getCompetitorId();

    Role getRole();

    enum Role {
        CONSTRUCTOR,
        OTHER,
    }
}
