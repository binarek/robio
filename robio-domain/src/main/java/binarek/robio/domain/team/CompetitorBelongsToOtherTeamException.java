package binarek.robio.domain.team;

import binarek.robio.domain.common.DomainException;
import binarek.robio.domain.person.PersonId;

public class CompetitorBelongsToOtherTeamException extends DomainException {

    public CompetitorBelongsToOtherTeamException(PersonId competitorId) {
        super("Competitor with id " + competitorId + " already belongs to other team");
    }
}
