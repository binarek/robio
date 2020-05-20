package binarek.robio.core.domain.team;

import binarek.robio.common.domain.entity.EntityServiceHelper;
import binarek.robio.core.domain.robot.RobotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeamService {

    private final EntityServiceHelper<Team, TeamFetchLevel> serviceHelper;
    private final RobotRepository robotRepository;

    public TeamService(TeamRepository teamRepository, RobotRepository robotRepository) {
        this.serviceHelper = new EntityServiceHelper<>(teamRepository, Team.ENTITY_NAME);
        this.robotRepository = robotRepository;
    }

    public Team createTeam(Team team) {
        return serviceHelper.createEntity(team);
    }

    public Team saveTeam(Team team) {
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
}