package binarek.robio.core.domain.team;

import binarek.robio.common.domain.DomainException;
import binarek.robio.user.domain.person.PersonId;

public class CompetitorBelongsToOtherTeamException extends DomainException {

    public CompetitorBelongsToOtherTeamException(PersonId competitorId) {
        super("Competitor with id " + competitorId + " already belongs to other team");
    }
}
