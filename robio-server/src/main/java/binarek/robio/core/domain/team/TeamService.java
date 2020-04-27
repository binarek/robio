package binarek.robio.core.domain.team;

import binarek.robio.common.domain.DomainEntityDetailsLevel;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team createTeam(Team team) {
        if (teamRepository.existsByIdOrName(team.getId(), team.getName())) {
            throw new TeamAlreadyExistsException(team.getId(), team.getName());
        }
        return teamRepository.insert(team);
    }

    public Team saveTeam(Team team) {
        if (team.getId() == null && teamRepository.existsByName(team.getName())) {
            throw new TeamAlreadyExistsException(team.getId(), team.getName());
        }
        return teamRepository.insertOrUpdate(team);
    }

    public void deleteTeam(UUID id) {
        if (!teamRepository.delete(id)) {
            throw new TeamNotExistsException(id);
        }
    }

    public <T extends TeamBasicInfo> T getTeam(UUID id, DomainEntityDetailsLevel domainEntityDetailsLevel, Class<T> resultType) {
        return teamRepository.getById(id, domainEntityDetailsLevel)
                .map(resultType::cast)
                .orElseThrow(() -> new TeamNotExistsException(id));
    }
}