package binarek.robio.registration.domain.competitor;

import binarek.robio.common.core.shared.AggregateRootNotExistsException;

public class CompetitorNotExistsException extends AggregateRootNotExistsException {

    public static CompetitorNotExistsException ofId(CompetitorId competitorId) {
        return new CompetitorNotExistsException("Competitor with id " + competitorId.getValue() + " does not exist");
    }

    public static CompetitorNotExistsException ofEmail(Email email) {
        return new CompetitorNotExistsException("Competitor with email " + email.getValue() + " does not exist");
    }

    private CompetitorNotExistsException(String message) {
        super(message);
    }
}
