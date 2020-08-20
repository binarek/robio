package binarek.robio.registration.domain.team;

import binarek.robio.common.domain.entity.Entity;
import binarek.robio.common.domain.value.Notes;
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
