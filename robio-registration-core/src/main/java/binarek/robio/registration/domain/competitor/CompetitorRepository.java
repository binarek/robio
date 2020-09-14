package binarek.robio.registration.domain.competitor;

import java.util.Optional;

public interface CompetitorRepository {

    void save(Competitor competitor);

    boolean existsById(CompetitorId competitorId);

    boolean existsByEmail(Email email);

    Optional<Competitor> getById(CompetitorId competitorId);

    Optional<Competitor> getByEmail(Email email);
}
