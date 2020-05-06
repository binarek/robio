package binarek.robio.core.persistence.team;

import binarek.robio.core.domain.team.Team;
import binarek.robio.core.domain.team.TeamWithAssociations;
import binarek.robio.db.tables.records.TeamMemberRecord;
import binarek.robio.db.tables.records.TeamRecord;
import binarek.robio.mapstruct.BaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.UUID;

@Mapper(config = BaseMapperConfig.class, uses = TeamMemberRecordMapper.class)
public interface TeamRecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    void updateRecord(@MappingTarget TeamRecord teamRecord, Team team);

    @Mapping(target = "id", source = "teamRecord.externalId")
    @Mapping(target = "members", source = "membersRecords")
    @Mapping(target = "robotsIds", source = "robotsIds")
    TeamWithAssociations toTeam(TeamRecord teamRecord, List<TeamMemberRecord> membersRecords, List<UUID> robotsIds);
}
