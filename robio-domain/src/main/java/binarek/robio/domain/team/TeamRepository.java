package binarek.robio.domain.team;

import binarek.robio.domain.common.entity.EntityRepository;
import binarek.robio.domain.person.PersonId;

public interface TeamRepository extends EntityRepository<Team, TeamFetchProperties, TeamId, TeamName> {

    boolean existsById(TeamId id);

    boolean doesCompetitorBelongToAnyTeam(PersonId competitorId);

    boolean doesCompetitorBelongToOtherTeam(PersonId competitorId, TeamId teamId);
}
