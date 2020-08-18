package binarek.robio.registration.domain.team;

import binarek.robio.registration.domain.common.entity.Entity;
import binarek.robio.registration.domain.common.value.Notes;
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
