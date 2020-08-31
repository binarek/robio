package binarek.robio.registration.app;

import binarek.robio.registration.domain.competitor.*;
import org.springframework.stereotype.Service;

@Service
public class CompetitorAppService {

    private final CompetitorRepository competitorRepository;
    private final CompetitorIdFactory competitorIdentityFactory;

    public CompetitorAppService(CompetitorRepository competitorRepository,
                                CompetitorIdFactory competitorIdentityFactory) {
        this.competitorRepository = competitorRepository;
        this.competitorIdentityFactory = competitorIdentityFactory;
    }

    public Competitor getCompetitor(CompetitorId competitorId) {
        return competitorRepository.getById(competitorId)
                .orElseThrow(() -> new CompetitorWithIdNotExistsException(competitorId));
    }

    public CompetitorId createCompetitor(FirstName firstName, LastName lastName, boolean isUnderage) {
        var competitorId = competitorIdentityFactory.generateCompetitorId();
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
