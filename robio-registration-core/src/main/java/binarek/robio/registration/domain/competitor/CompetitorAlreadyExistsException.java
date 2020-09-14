package binarek.robio.registration.domain.competitor;

import binarek.robio.common.domain.AggregateRootAlreadyExistsException;

public class CompetitorAlreadyExistsException extends AggregateRootAlreadyExistsException {

    public static CompetitorAlreadyExistsException ofEmail(Email email) {
        return new CompetitorAlreadyExistsException("Competitor with email " + email.getValue() + " already exists");
    }

    private CompetitorAlreadyExistsException(String message) {
        super(message);
    }
}
