package binarek.robio.core.domain.team;

import binarek.robio.common.domain.DomainException;

import java.util.UUID;

public class CompetitorBelongsToOtherTeamException extends DomainException {

    public CompetitorBelongsToOtherTeamException(UUID competitorId) {
        super("Competitor with id " + competitorId + " already belongs to other team");
    }
}
