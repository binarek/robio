package binarek.robio.core.domain.team;

import binarek.robio.common.domain.entity.EntityNotExistsException;
import binarek.robio.common.domain.entity.EntityServiceHelper;
import binarek.robio.core.domain.robot.RobotRepository;
import binarek.robio.user.domain.person.Person;
import binarek.robio.user.domain.person.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static binarek.robio.user.domain.person.Person.Role.COMPETITOR;

@Service
public class TeamService {

    private final EntityServiceHelper<Team, TeamFetchLevel> serviceHelper;
    private final TeamRepository teamRepository;
    private final PersonRepository personRepository;
    private final RobotRepository robotRepository;

    public TeamService(TeamRepository teamRepository, PersonRepository personRepository, RobotRepository robotRepository) {
        this.serviceHelper = new EntityServiceHelper<>(Team.class, teamRepository);
        this.teamRepository = teamRepository;
        this.personRepository = personRepository;
        this.robotRepository = robotRepository;
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

    public void deleteTeam(UUID id) {
        if (robotRepository.existsByTeamId(id)) {
            throw new TeamHasRobotsException(id);
        }
        serviceHelper.deleteEntity(id);
    }

    public Team getTeam(UUID id) {
        return serviceHelper.getEntity(id, TeamFetchLevel.TEAM);
    }

    public TeamBasicInfo getTeamBasicInfo(UUID id) {
        return serviceHelper.getEntity(id, TeamFetchLevel.TEAM_BASIC_INFO);
    }

    public List<UUID> getTeamRobotsIds(UUID id) {
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
}