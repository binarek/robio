package binarek.robio.registration.domain.team;

import binarek.robio.common.codegen.BaseStyle;
import binarek.robio.registration.domain.common.entity.Entity;
import binarek.robio.registration.domain.common.value.Notes;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableTeam.class)
public abstract class Team implements Entity, TeamBasicInfo {

    Team() {
    }

    @Nullable
    public abstract TeamId getId();

    @Nullable
    public abstract Long getVersion();

    public abstract TeamName getName();

    public abstract List<TeamMember> getMembers();

    @Nullable
    public abstract Notes getNotes();

    @Value.Check
    protected void validate() {
        var noTeamMemberDuplicates = getMembers().stream()
                .map(TeamMember::getCompetitorId)
                .allMatch(new HashSet<>()::add);
        Assert.state(noTeamMemberDuplicates, "Team cannot have duplicated members");
    }
}
