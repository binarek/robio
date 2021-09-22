package binarek.robio.core.persistence.team;

import binarek.robio.codegen.BaseMapperConfig;
import binarek.robio.common.domain.value.CommonValueMapper;
import binarek.robio.core.domain.team.Team;
import binarek.robio.core.domain.team.TeamSortableField;
import binarek.robio.core.domain.team.TeamValueMapper;
import binarek.robio.db.tables.records.TeamMemberRecord;
import binarek.robio.db.tables.records.TeamRecord;
import org.jooq.TableField;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static binarek.robio.core.domain.team.TeamSortableField.NAME;
import static binarek.robio.db.tables.Team.TEAM;

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
