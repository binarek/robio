package binarek.robio.core.domain.team;

import binarek.robio.codegen.BaseStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutableTeam.class)
public interface Team extends TeamBasicInfo {

    List<TeamMember> getMembers();
}
