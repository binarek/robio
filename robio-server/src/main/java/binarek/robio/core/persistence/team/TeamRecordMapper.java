package binarek.robio.core.persistence.team;

import binarek.robio.codegen.BaseMapperConfig;
import binarek.robio.common.domain.value.StandardValueMapper;
import binarek.robio.core.domain.team.Team;
import binarek.robio.core.domain.team.TeamName;
import binarek.robio.db.tables.records.TeamMemberRecord;
import binarek.robio.db.tables.records.TeamRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = BaseMapperConfig.class, uses = {StandardValueMapper.class, TeamMemberRecordMapper.class})
public interface TeamRecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    void updateRecord(@MappingTarget TeamRecord teamRecord, Team team);

    @Mapping(target = "id", source = "teamRecord.externalId")
    @Mapping(target = "members", source = "membersRecords")
    Team toTeam(TeamRecord teamRecord, List<TeamMemberRecord> membersRecords);

    default TeamName toTeamName(String name) {
        return TeamName.of(name);
    }

    default String toValue(TeamName name) {
        return name.getValue();
    }
}
