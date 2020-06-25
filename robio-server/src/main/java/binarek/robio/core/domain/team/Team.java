package binarek.robio.core.domain.team;

import binarek.robio.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableTeam.class)
public interface Team extends TeamBasicInfo {

    List<TeamMember> getMembers();

    @Value.Check
    default void validate() {
        var noTeamMemberDuplicates = getMembers().stream()
                .map(TeamMember::getCompetitorId)
                .allMatch(new HashSet<>()::add);
        Assert.state(noTeamMemberDuplicates, "Team cannot have duplicated members");
    }
}
