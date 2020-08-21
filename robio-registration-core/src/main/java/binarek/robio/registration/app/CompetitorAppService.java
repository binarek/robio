package binarek.robio.registration.app;

import binarek.robio.common.domain.exception.AggregateRootNotExistsException;
import binarek.robio.registration.domain.*;
import org.springframework.stereotype.Service;

@Service
public class CompetitorAppService {

    private final CompetitorRepository competitorRepository;
    private final IdentityFactory identityFactory;

    public CompetitorAppService(CompetitorRepository competitorRepository, IdentityFactory identityFactory) {
        this.competitorRepository = competitorRepository;
        this.identityFactory = identityFactory;
    }

    public Competitor getCompetitor(CompetitorId competitorId) {
        return competitorRepository.getById(competitorId)
                .orElseThrow(() -> new AggregateRootNotExistsException(Competitor.class, competitorId));
    }

    public CompetitorId createCompetitor(FirstName firstName, LastName lastName, boolean isUnderage) {
        var competitorId = identityFactory.generateCompetitorId();
        var competitor = Competitor.newCompetitor(competitorId, firstName, lastName, isUnderage);
        competitorRepository.save(competitor);
        return competitorId;
    }

    public void approveByOwner(CompetitorId competitorId) {
        var competitor = getCompetitor(competitorId);
        competitorRepository.save(competitor.approveByOwner());
    }

    public void approveByParent(CompetitorId competitorId) {
        var competitor = getCompetitor(competitorId);
        competitorRepository.save(competitor.approveByParent());
    }
}
