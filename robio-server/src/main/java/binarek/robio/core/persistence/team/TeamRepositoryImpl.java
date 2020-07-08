package binarek.robio.core.persistence.team;

import binarek.robio.common.persistence.EntityTableHelper;
import binarek.robio.core.domain.team.*;
import binarek.robio.db.tables.records.TeamMemberRecord;
import binarek.robio.db.tables.records.TeamRecord;
import binarek.robio.user.domain.person.PersonId;
import org.jooq.DSLContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static binarek.robio.common.persistence.EntityPersistenceUtil.*;
import static binarek.robio.db.tables.Team.TEAM;
import static binarek.robio.db.tables.TeamMember.TEAM_MEMBER;

@Repository
public class TeamRepositoryImpl implements TeamRepository {

    private final DSLContext dsl;
    private final TeamTableMapper teamTableMapper;
    private final TeamMemberTableMapper teamMemberTableMapper;
    private final EntityTableHelper<TeamRecord> teamTableHelper;

    public TeamRepositoryImpl(DSLContext dsl,
                              TeamTableMapper teamTableMapper,
                              TeamMemberTableMapper teamMemberTableMapper) {
        this.dsl = dsl;
        this.teamTableMapper = teamTableMapper;
        this.teamMemberTableMapper = teamMemberTableMapper;
        this.teamTableHelper = new EntityTableHelper<>(Team.class, dsl, TEAM);
    }

    @Override
    public Optional<Team> getById(TeamId id, @Nullable TeamFetchProperties fetchProperties) {
        return teamTableHelper.getByExternalId(id.getValue())
                .map(teamRecord -> teamTableMapper.toTeam(
                        teamRecord,
                        fetchMembers(fetchProperties) ? fetchMembersRecords(teamRecord.getId()) : List.of()));
    }

    @Override
    public List<? extends Team> getAll(@Nullable TeamFetchProperties fetchProperties) {
        var teamRecords = getTeamRecords(fetchProperties);
        Map<Long, List<TeamMemberRecord>> membersByTeamId = fetchMembers(fetchProperties) ?
                fetchMembersRecords(teamRecords.stream().map(TeamRecord::getId).collect(Collectors.toList())) :
                Map.of();

        return teamRecords.stream()
                .map(record -> teamTableMapper.toTeam(record, membersByTeamId.get(record.getId())))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean existsById(TeamId id) {
        return teamTableHelper.existsByExternalId(id.getValue());
    }

    @Override
    public boolean existsByName(TeamName name) {
        return teamTableHelper.existsByName(name.getValue());
    }

    @Override
    public boolean existsByIdOrName(@Nullable TeamId id, TeamName name) {
        return teamTableHelper.existsByExternalIdOrName(getValueNullSafe(id), name.getValue());
    }

    @Override
    @Transactional
    public Team insert(Team team) {
        var teamRecord = teamTableHelper.insert(record -> teamTableMapper.updateRecord(record, team));
        var teamMembers = insertMembers(team.getMembers(), teamRecord.getId());
        return teamTableMapper.toTeam(teamRecord, teamMembers);
    }

    @Override
    @Transactional
    public Team insertOrUpdate(Team team) {
        var teamRecord = teamTableHelper.insertOrUpdate(getValueNullSafe(team.getId()),
                record -> teamTableMapper.updateRecord(record, team));
        var teamMembers = insertOrUpdateMembers(team.getMembers(), teamRecord.getId());
        return teamTableMapper.toTeam(teamRecord, teamMembers);
    }

    @Override
    @Transactional
    public boolean deleteById(TeamId id) {
        deleteMembersByTeamExternalId(id.getValue());
        return teamTableHelper.deleteByExternalId(id.getValue());
    }

    @Override
    public boolean doesCompetitorBelongToAnyTeam(PersonId competitorId) {
        return dsl.selectOne()
                .from(TEAM_MEMBER)
                .where(TEAM_MEMBER.COMPETITOR_ID.eq(competitorId.getValue()))
                .fetchOne() != null;
    }

    @Override
    public boolean doesCompetitorBelongToOtherTeam(PersonId competitorId, TeamId teamId) {
        return dsl.selectOne()
                .from(TEAM_MEMBER)
                .where(TEAM_MEMBER.COMPETITOR_ID.eq(competitorId.getValue()))
                .andNot(TEAM_MEMBER.TEAM_ID.eq(
                        dsl.select(TEAM.ID).from(TEAM).where(TEAM.EXTERNAL_ID.eq(teamId.getValue()))
                ))
                .fetchOne() != null;
    }

    private List<TeamMemberRecord> fetchMembersRecords(Long teamId) {
        return dsl.fetch(TEAM_MEMBER, TEAM_MEMBER.TEAM_ID.eq(teamId));
    }

    private Map<Long, List<TeamMemberRecord>> fetchMembersRecords(List<Long> teamIds) {
        return dsl.fetch(TEAM_MEMBER, TEAM_MEMBER.TEAM_ID.in(teamIds)).stream()
                .collect(Collectors.groupingBy(TeamMemberRecord::getTeamId, Collectors.toList()));
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
        teamMemberTableMapper.updateRecord(record, teamMember, teamId);
        return record;
    }

    private List<TeamRecord> getTeamRecords(@Nullable TeamFetchProperties fetchProperties) {
        return teamTableHelper.getAll(
                getLimit(fetchProperties),
                getOffset(fetchProperties),
                getSort(fetchProperties, teamTableMapper::toField));
    }

    private static boolean fetchMembers(@Nullable TeamFetchProperties fetchProperties) {
        return Optional.ofNullable(fetchProperties)
                .map(TeamFetchProperties::getDetailsLevel)
                .map(TeamFetchProperties.DetailsLevel.TEAM::equals)
                .orElse(false);
    }

    @Nullable
    private static UUID getValueNullSafe(@Nullable TeamId teamId) {
        return teamId != null ? teamId.getValue() : null;
    }
}
