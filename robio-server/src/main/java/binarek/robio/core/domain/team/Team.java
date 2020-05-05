package binarek.robio.core.domain.team;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonDeserialize(as = ImmutableTeamWithAssociations.class)
public interface Team extends TeamBasicInfo {

    String ENTITY_NAME = "Team";

    List<TeamMember> getMembers();
}
