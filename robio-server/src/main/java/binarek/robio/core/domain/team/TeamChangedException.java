package binarek.robio.core.domain.team;

import binarek.robio.common.domain.DomainEntityChangedException;

import java.util.UUID;

public class TeamChangedException extends DomainEntityChangedException {

    public TeamChangedException(UUID id) {
        super("Team", id);
    }
}
