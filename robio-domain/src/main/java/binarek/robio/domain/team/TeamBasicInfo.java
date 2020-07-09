package binarek.robio.domain.team;

import binarek.robio.domain.common.entity.Entity;
import binarek.robio.domain.common.value.Notes;
import org.springframework.lang.Nullable;

public interface TeamBasicInfo extends Entity {

    @Nullable
    TeamId getId();

    @Nullable
    Long getVersion();

    TeamName getName();

    @Nullable
    Notes getNotes();
}
