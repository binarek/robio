package binarek.robio.registration.test;

import binarek.robio.registration.domain.Competitor;
import binarek.robio.registration.domain.CompetitorId;
import binarek.robio.registration.domain.CompetitorRepository;
import binarek.robio.registration.domain.IdentityFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Configuration  // TODO to remove after creating some final implementation of beans
public class TestConfiguration {

    @Bean
    IdentityFactory identityFactory() {
        return () -> CompetitorId.of(UUID.randomUUID());
    }

    @Bean
    CompetitorRepository competitorRepository() {
        return new CompetitorRepository() {

            private final Map<CompetitorId, Competitor> map = new HashMap<>();

            @Override
            public void save(Competitor competitor) {
                map.put(competitor.getId(), competitor);
            }

            @Override
            public boolean existsById(CompetitorId competitorId) {
                return map.containsKey(competitorId);
            }

            @Override
            public Optional<Competitor> getById(CompetitorId competitorId) {
                return Optional.ofNullable(map.get(competitorId));
            }
        };
    }

}
