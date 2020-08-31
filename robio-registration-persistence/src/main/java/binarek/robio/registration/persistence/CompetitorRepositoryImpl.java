package binarek.robio.registration.persistence;

import binarek.robio.registration.domain.competitor.Competitor;
import binarek.robio.registration.domain.competitor.CompetitorId;
import binarek.robio.registration.domain.competitor.CompetitorRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CompetitorRepositoryImpl implements CompetitorRepository {

    private final Map<CompetitorId, Competitor> competitors = new HashMap<>();  // TODO use db

    @Override
    public void save(Competitor competitor) {
        competitors.put(competitor.getId(), competitor);
    }

    @Override
    public boolean existsById(CompetitorId competitorId) {
        return competitors.containsKey(competitorId);
    }

    @Override
    public Optional<Competitor> getById(CompetitorId competitorId) {
        return Optional.ofNullable(competitors.get(competitorId));
    }
}
