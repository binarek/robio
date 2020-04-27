package binarek.robio.core.domain.team;

import binarek.robio.common.domain.DomainEntityNotExistsException;

import java.util.UUID;

public class TeamNotExistsException extends DomainEntityNotExistsException {

    public TeamNotExistsException(UUID id) {
        super("Team", id);
    }
}
