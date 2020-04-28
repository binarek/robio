package binarek.robio.core.domain.team;

import binarek.robio.common.domain.DomainException;

import java.util.UUID;

public class TeamHasRobotsException extends DomainException {

    public TeamHasRobotsException(UUID id) {
        super("Team with id " + id + " has assigned robots");
    }
}
