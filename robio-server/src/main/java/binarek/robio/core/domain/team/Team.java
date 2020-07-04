package binarek.robio.core.domain.team;

import binarek.robio.codegen.BaseStyle;
import binarek.robio.common.domain.entity.Entity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableTeam.class)
public abstract class Team implements Entity, TeamBasicInfo {

    Team() {
    }

    @Override
    public final UUID getIdValue() {
        return getId() != null ? getId().getValue() : null;
    }

    @Override
    public final String getNameValue() {
        return getName().getValue();
    }

    @Nullable
    public abstract TeamId getId();

    public abstract List<TeamMember> getMembers();

    @Value.Check
    protected void validate() {
        var noTeamMemberDuplicates = getMembers().stream()
                .map(TeamMember::getCompetitorId)
                .allMatch(new HashSet<>()::add);
        Assert.state(noTeamMemberDuplicates, "Team cannot have duplicated members");
    }
}
