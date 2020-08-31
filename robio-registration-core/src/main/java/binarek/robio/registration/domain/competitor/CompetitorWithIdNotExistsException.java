package binarek.robio.registration.domain.competitor;

import binarek.robio.common.domain.exception.DomainException;

public class CompetitorWithIdNotExistsException extends DomainException {

    public CompetitorWithIdNotExistsException(CompetitorId competitorId) {
        super("Competitor with id " + competitorId.getValue() + " does not exist");
    }
}
