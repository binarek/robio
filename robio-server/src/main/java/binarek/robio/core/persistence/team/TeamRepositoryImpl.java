package binarek.robio.core.persistence.team;

import binarek.robio.common.persistence.EntityTableHelper;
import binarek.robio.core.domain.team.Team;
import binarek.robio.core.domain.team.TeamFetchLevel;
import binarek.robio.core.domain.team.TeamMember;
import binarek.robio.core.domain.team.TeamRepository;
import binarek.robio.db.tables.records.TeamMemberRecord;
import binarek.robio.db.tables.records.TeamRecord;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static binarek.robio.db.tables.Robot.ROBOT;
import static binarek.robio.db.tables.Team.TEAM;
import static binarek.robio.db.tables.TeamMember.TEAM_MEMBER;

@Repository
public class TeamRepositoryImpl implements TeamRepository {

    private final DSLContext dsl;
    private final TeamRecordMapper teamRecordMapper;
    private final TeamMemberRecordMapper teamMemberRecordMapper;
    private final EntityTableHelper<TeamRecord> teamTableHelper;

    public TeamRepositoryImpl(DSLContext dsl,
                              TeamRecordMapper teamRecordMapper,
                              TeamMemberRecordMapper teamMemberRecordMapper) {
        this.dsl = dsl;
        this.teamRecordMapper = teamRecordMapper;
        this.teamMemberRecordMapper = teamMemberRecordMapper;
        this.teamTableHelper = new EntityTableHelper<>(Team.class, dsl, TEAM);
    }

    @Override
    public Optional<Team> getById(UUID id, @Nullable TeamFetchLevel fetchLevel) {
        return teamTableHelper.getByExternalId(id)
                .map(teamRecord -> teamRecordMapper.toTeam(
                        teamRecord,
                        fetchLevel == TeamFetchLevel.TEAM ? fetchMembersRecords(teamRecord.getId()) : List.of()));
    }

    @Override
    public boolean existsById(UUID id) {
        return teamTableHelper.existsByExternalId(id);
    }

    @Override
    public boolean existsByName(String name) {
        return teamTableHelper.existsByName(name);
    }

    @Override
    public boolean existsByIdOrName(@Nullable UUID id, String name) {
        return teamTableHelper.existsByExternalIdOrName(id, name);
    }

    @Override
    @Transactional
    public Team insert(Team team) {
        var teamRecord = teamTableHelper.insert(record -> teamRecordMapper.updateRecord(record, team));
        var teamMembers = insertMembers(team.getMembers(), teamRecord.getId());
        return teamRecordMapper.toTeam(teamRecord, teamMembers);
    }

    @Override
    @Transactional
    public Team insertOrUpdate(Team team) {
        var teamRecord = teamTableHelper.insertOrUpdate(team.getId(), record -> teamRecordMapper.updateRecord(record, team));
        var teamMembers = insertOrUpdateMembers(team.getMembers(), teamRecord.getId());
        return teamRecordMapper.toTeam(teamRecord, teamMembers);
    }

    @Override
    @Transactional
    public boolean deleteById(UUID id) {
        deleteMembersByTeamExternalId(id);
        return teamTableHelper.deleteByExternalId(id);
    }

    private List<TeamMemberRecord> fetchMembersRecords(Long teamId) {
        return dsl.fetch(TEAM_MEMBER, TEAM_MEMBER.TEAM_ID.eq(teamId));
    }

    private List<UUID> fetchRobotsIds(UUID teamExternalId) {
        return dsl.select(ROBOT.EXTERNAL_ID)
                .from(ROBOT)
                .where(ROBOT.TEAM_ID.eq(teamExternalId))
                .fetch()
                .map(Record1::value1);
    }

    private List<TeamMemberRecord> insertMembers(List<TeamMember> teamMembers, Long teamId) {
        if (!teamMembers.isEmpty()) {
            var memberRecords = teamMembers.stream()
                    .map(member -> createRecord(member, teamId))
                    .collect(Collectors.toList());
            dsl.batchStore(memberRecords).execute();
            return memberRecords;
        } else {
            return List.of();
        }
    }

    private List<TeamMemberRecord> insertOrUpdateMembers(List<TeamMember> teamMembers, Long teamId) {
        deleteMembersByTeamId(teamId);
        return insertMembers(teamMembers, teamId);
    }

    private void deleteMembersByTeamId(Long teamId) {
        dsl.deleteFrom(TEAM_MEMBER)
                .where(TEAM_MEMBER.TEAM_ID.eq(teamId))
                .execute();
    }

    private void deleteMembersByTeamExternalId(UUID teamExternalId) {
        dsl.deleteFrom(TEAM_MEMBER)
                .where(TEAM_MEMBER.TEAM_ID.eq(
                        dsl.select(TEAM.ID).from(TEAM).where(TEAM.EXTERNAL_ID.eq(teamExternalId))
                ))
                .execute();
    }

    private TeamMemberRecord createRecord(TeamMember teamMember, Long teamId) {
        var record = dsl.newRecord(TEAM_MEMBER);
        teamMemberRecordMapper.updateRecord(record, teamMember, teamId);
        return record;
    }
}
