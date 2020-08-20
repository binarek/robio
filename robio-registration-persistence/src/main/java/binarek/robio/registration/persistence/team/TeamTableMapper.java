package binarek.robio.registration.persistence.team;

import binarek.robio.common.codegen.BaseMapperConfig;
import binarek.robio.registration.domain.team.Team;
import binarek.robio.registration.domain.team.TeamSortableField;
import binarek.robio.registration.domain.team.TeamValueMapper;
import binarek.robio.common.domain.value.CommonValueMapper;
import binarek.robio.db.tables.records.TeamMemberRecord;
import binarek.robio.db.tables.records.TeamRecord;
import org.jooq.TableField;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static binarek.robio.db.tables.Team.TEAM;
import static binarek.robio.registration.domain.team.TeamSortableField.NAME;

@Mapper(config = BaseMapperConfig.class,
        uses = {CommonValueMapper.class, TeamValueMapper.class, TeamMemberTableMapper.class})
public interface TeamTableMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    void updateRecord(@MappingTarget TeamRecord teamRecord, Team team);

    @Mapping(target = "id", source = "teamRecord.externalId")
    @Mapping(target = "members", source = "membersRecords")
    Team toTeam(TeamRecord teamRecord, List<TeamMemberRecord> membersRecords);

    default TableField<TeamRecord, ?> toField(TeamSortableField teamSortableField) {
        if (teamSortableField == NAME) {
            return TEAM.NAME;
        } else {
            throw new IllegalArgumentException("Field of type " + teamSortableField + " is not supported");
        }
    }
}
