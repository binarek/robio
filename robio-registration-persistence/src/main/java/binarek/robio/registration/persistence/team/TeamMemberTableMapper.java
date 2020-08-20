package binarek.robio.registration.persistence.team;

import binarek.robio.common.codegen.BaseMapperConfig;
import binarek.robio.registration.domain.person.PersonValueMapper;
import binarek.robio.registration.domain.team.TeamMember;
import binarek.robio.common.domain.value.CommonValueMapper;
import binarek.robio.db.tables.records.TeamMemberRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapperConfig.class, uses = {CommonValueMapper.class, PersonValueMapper.class})
public interface TeamMemberTableMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teamId", source = "teamId")
    void updateRecord(@MappingTarget TeamMemberRecord teamMemberRecord, TeamMember teamMember, Long teamId);

    TeamMember toTeamMember(TeamMemberRecord teamMemberRecord);
}
