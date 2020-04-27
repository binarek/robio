package binarek.robio.core.persistence.team;

import binarek.robio.core.domain.team.TeamMember;
import binarek.robio.db.tables.records.TeamMemberRecord;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TeamMemberRecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teamId", source = "teamId")
    void updateRecord(@MappingTarget TeamMemberRecord teamMemberRecord, TeamMember teamMember, Long teamId);

    TeamMember toTeamMember(TeamMemberRecord teamMemberRecord);
}
