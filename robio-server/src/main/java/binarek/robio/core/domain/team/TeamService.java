package binarek.robio.core.domain.team;

import binarek.robio.common.domain.DomainEntityDetailsLevel;
import binarek.robio.common.domain.DomainEntityServiceHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TeamService {

    private final DomainEntityServiceHelper<Team, TeamBasicInfo> serviceHelper;

    public TeamService(TeamRepository teamRepository) {
        this.serviceHelper = new DomainEntityServiceHelper<>(teamRepository,
                TeamNotExistsException::new, TeamAlreadyExistsException::new);
    }

    public Team createTeam(Team team) {
        return serviceHelper.createEntity(team);
    }

    public Team saveTeam(Team team) {
        return serviceHelper.saveEntity(team);
    }

    public void deleteTeam(UUID id) {
        serviceHelper.deleteEntity(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends TeamBasicInfo> T getTeam(UUID id, DomainEntityDetailsLevel detailsLevel, Class<? extends T> resultType) {
        return (T) serviceHelper.getEntity(id, detailsLevel, resultType);
    }
}