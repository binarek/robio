package binarek.robio.core.persistence.team;

import binarek.robio.core.domain.team.TeamSortableField;
import binarek.robio.db.tables.records.TeamRecord;
import org.jooq.TableField;
import org.springframework.stereotype.Component;

import static binarek.robio.core.domain.team.TeamSortableField.NAME;
import static binarek.robio.db.tables.Team.TEAM;

@Component
public class TeamTableFieldMapper {

    public TableField<TeamRecord, ?> toField(TeamSortableField teamSortableField) {
        if (teamSortableField == NAME) {
            return TEAM.NAME;
        } else {
            throw new IllegalArgumentException("Field of type " + teamSortableField + " is not supported");
        }
    }
}
