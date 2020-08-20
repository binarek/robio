package binarek.robio.registration.domain.team;

import binarek.robio.common.domain.entity.EntityRepository;
import binarek.robio.registration.domain.person.PersonId;

public interface TeamRepository extends EntityRepository<Team, TeamFetchProperties, TeamId, TeamName> {

    boolean existsById(TeamId id);

    boolean doesCompetitorBelongToAnyTeam(PersonId competitorId);

    boolean doesCompetitorBelongToOtherTeam(PersonId competitorId, TeamId teamId);
}
