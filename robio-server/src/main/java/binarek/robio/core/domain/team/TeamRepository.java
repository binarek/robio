package binarek.robio.core.domain.team;

import binarek.robio.common.domain.entity.EntityRepository;

import java.util.UUID;

public interface TeamRepository extends EntityRepository<Team, TeamFetchLevel> {

    boolean existsById(UUID id);

    boolean doesCompetitorBelongToAnyTeam(UUID competitorId);

    boolean doesCompetitorBelongToOtherTeam(UUID competitorId, UUID teamId);
}
