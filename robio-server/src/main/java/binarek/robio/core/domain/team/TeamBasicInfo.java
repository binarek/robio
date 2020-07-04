package binarek.robio.core.domain.team;

import binarek.robio.common.domain.entity.Entity;
import org.springframework.lang.Nullable;

public interface TeamBasicInfo extends Entity {

    @Nullable
    TeamId getId();

    TeamName getName();
}
