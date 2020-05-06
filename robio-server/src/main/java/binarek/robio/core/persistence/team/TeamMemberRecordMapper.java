package binarek.robio.core.persistence.team;

import binarek.robio.core.domain.team.TeamMember;
import binarek.robio.db.tables.records.TeamMemberRecord;
import binarek.robio.mapstruct.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapperConfig.class)
public interface TeamMemberRecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teamId", source = "teamId")
    void updateRecord(@MappingTarget TeamMemberRecord teamMemberRecord, TeamMember teamMember, Long teamId);

    TeamMember toTeamMember(TeamMemberRecord teamMemberRecord);
}
