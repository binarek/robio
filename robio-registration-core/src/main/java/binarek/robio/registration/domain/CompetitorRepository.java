package binarek.robio.registration.domain;

import java.util.Optional;

public interface CompetitorRepository {

    void save(Competitor competitor);

    boolean existsById(CompetitorId competitorId);

    Optional<Competitor> getById(CompetitorId competitorId);
}
