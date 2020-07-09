package binarek.robio.domain.team;

import binarek.robio.domain.common.DomainException;

public class TeamHasRobotsException extends DomainException {

    public TeamHasRobotsException(TeamId id) {
        super("Team with id " + id + " has assigned robots");
    }
}
