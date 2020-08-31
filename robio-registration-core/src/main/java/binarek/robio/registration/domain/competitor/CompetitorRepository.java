package binarek.robio.registration.domain.competitor;

import java.util.Optional;

public interface CompetitorRepository {

    void save(Competitor competitor);

    boolean existsById(CompetitorId competitorId);

    Optional<Competitor> getById(CompetitorId competitorId);
}
