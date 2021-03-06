package binarek.robio.core.domain.team;

import binarek.robio.common.domain.entity.EntityNotExistsException;
import binarek.robio.common.domain.entity.EntityServiceHelper;
import binarek.robio.common.domain.entity.EntityValueExtractor;
import binarek.robio.core.domain.robot.RobotId;
import binarek.robio.core.domain.robot.RobotRepository;
import binarek.robio.user.domain.person.Person;
import binarek.robio.user.domain.person.PersonRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

import static binarek.robio.core.domain.team.TeamFetchProperties.DetailsLevel.TEAM;
import static binarek.robio.core.domain.team.TeamFetchProperties.DetailsLevel.TEAM_BASIC_INFO;
import static binarek.robio.user.domain.person.Person.Role.COMPETITOR;

@Service
public class TeamService {

    private final EntityServiceHelper<Team, TeamFetchProperties, TeamId, TeamName> serviceHelper;
    private final TeamRepository teamRepository;
    private final PersonRepository personRepository;
    private final RobotRepository robotRepository;

    public TeamService(TeamRepository teamRepository,
                       PersonRepository personRepository,
                       RobotRepository robotRepository) {
        this.teamRepository = teamRepository;
        this.personRepository = personRepository;
        this.robotRepository = robotRepository;
        this.serviceHelper = new EntityServiceHelper<>(Team.class, teamRepository, new TeamValueExtractor());
    }

    @Transactional
    public Team createTeam(Team team) {
        validateTeamMembers(team.getMembers(), member -> {
            var competitorId = member.getCompetitorId();
            if (teamRepository.doesCompetitorBelongToAnyTeam(competitorId)) {
                throw new CompetitorBelongsToOtherTeamException(competitorId);
            }
        });
        return serviceHelper.createEntity(team);
    }

    @Transactional
    public Team saveTeam(Team team) {
        validateTeamMembers(team.getMembers(), member -> {
            var competitorId = member.getCompetitorId();
            if (team.getId() != null && teamRepository.doesCompetitorBelongToOtherTeam(competitorId, team.getId())) {
                throw new CompetitorBelongsToOtherTeamException(competitorId);
            }
        });
        return serviceHelper.saveEntity(team);
    }

    public void deleteTeam(TeamId id) {
        if (robotRepository.existsByTeamId(id)) {
            throw new TeamHasRobotsException(id);
        }
        serviceHelper.deleteEntity(id);
    }

    public Team getTeam(TeamId id) {
        return serviceHelper.getEntity(id, TeamFetchProperties.of(TEAM));
    }

    public TeamBasicInfo getTeamBasicInfo(TeamId id) {
        return serviceHelper.getEntity(id, TeamFetchProperties.of(TEAM_BASIC_INFO));
    }

    public List<RobotId> getTeamRobotsIds(TeamId id) {
        return robotRepository.getIdsByTeamId(id);
    }

    private void validateTeamMembers(List<TeamMember> teamMembers, Consumer<TeamMember> extraValidation) {
        for (var member : teamMembers) {
            validateReferences(member);
            extraValidation.accept(member);
        }
    }

    private void validateReferences(TeamMember teamMember) {
        var competitorId = teamMember.getCompetitorId();
        if (!personRepository.existsByIdAndRole(competitorId, COMPETITOR)) {
            throw new EntityNotExistsException(Person.class, competitorId, "role " + COMPETITOR);
        }
    }

    private static final class TeamValueExtractor implements EntityValueExtractor<Team, TeamId, TeamName> {
        @Override
        @Nullable
        public TeamId getId(Team team) {
            return team.getId();
        }

        @Override
        public TeamName getName(Team team) {
            return team.getName();
        }
    }
}