package binarek.robio.core.domain.team;

import binarek.robio.common.domain.entity.Entity;

public interface TeamBasicInfo extends Entity {

    @Override
    default String getNameValue() {
        return getName().getValue();
    }

    TeamName getName();
}
