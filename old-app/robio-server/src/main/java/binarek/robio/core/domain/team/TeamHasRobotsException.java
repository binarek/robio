package binarek.robio.core.domain.team;

import binarek.robio.common.domain.DomainException;

public class TeamHasRobotsException extends DomainException {

    public TeamHasRobotsException(TeamId id) {
        super("Team with id " + id + " has assigned robots");
    }
}
