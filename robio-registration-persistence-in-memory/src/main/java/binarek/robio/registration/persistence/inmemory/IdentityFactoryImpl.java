package binarek.robio.registration.persistence.inmemory;

import binarek.robio.registration.domain.competitor.CompetitorId;
import binarek.robio.registration.domain.competitor.CompetitorIdFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdentityFactoryImpl implements CompetitorIdFactory {

    @Override
    public CompetitorId generateCompetitorId() {
        return CompetitorId.of(UUID.randomUUID());
    }
}
