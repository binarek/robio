package binarek.robio.registration.domain.team;

import binarek.robio.registration.domain.common.DomainException;

public class TeamHasRobotsException extends DomainException {

    public TeamHasRobotsException(TeamId id) {
        super("Team with id " + id + " has assigned robots");
    }
}
