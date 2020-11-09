package binarek.robio.ftl.domain.execution;

import binarek.robio.ftl.domain.execution.model.FtlParticipation;
import binarek.robio.ftl.domain.execution.model.FtlParticipationDetails;

import java.util.List;

public class FtlCompetitionService {

    private final FtlCompetitionRepository ftlCompetitionRepository;
    private final FtlRunRepository ftlRunRepository;

    public FtlCompetitionService(FtlCompetitionRepository ftlCompetitionRepository,
                                 FtlRunRepository ftlRunRepository) {
        this.ftlCompetitionRepository = ftlCompetitionRepository;
        this.ftlRunRepository = ftlRunRepository;
    }

    public boolean canAddNewRun(FtlParticipation participation) {
        var runsLimitPerRobot = ftlCompetitionRepository.getByCompetitionId(participation.competitionId())
                .orElseThrow()  // TODO
                .runsLimitPerRobot();
        return runsLimitPerRobot == null ||
                ftlRunRepository.countRunsForParticipation(participation) < runsLimitPerRobot;
    }

    public List<FtlParticipationDetails> getResults() {
        return List.of();   // TODO
    }
}
