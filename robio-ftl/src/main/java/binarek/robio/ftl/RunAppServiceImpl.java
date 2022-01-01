package binarek.robio.ftl;

import binarek.robio.ftl.command.AddRunCommand;
import binarek.robio.ftl.command.EditRunResultCommand;
import binarek.robio.ftl.exception.RunNotFoundException;
import binarek.robio.ftl.model.Run;
import binarek.robio.ftl.query.RunQuery;
import binarek.robio.ftl.view.RunView;
import binarek.robio.shared.exception.EntityHasChangedException;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

@Service
class RunAppServiceImpl implements RunAppService {

    private final RunRepository runRepository;
    private final RunService runService;

    RunAppServiceImpl(RunRepository runRepository,
                      RunService runService) {
        this.runRepository = runRepository;
        this.runService = runService;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
    @Transactional
    public Integer addRun(AddRunCommand command) {
        final var competitionId = command.getCompetitionId();
        final var robotId = command.getRobotId();

        runService.validateIfCanAddRun(competitionId, robotId);
        final var run = runService.getLastRun(competitionId, robotId)
                .map(lastRun -> Run.nextRun(lastRun, command.getJudgement(), command.getTime()))
                .orElseGet(() -> Run.firstRun(competitionId, robotId, command.getJudgement(), command.getTime()));
        runRepository.save(run);
        return run.getNumber();
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
    @Retryable(EntityHasChangedException.class)
    public void editRunResult(EditRunResultCommand command) {
        var run = getRun(command.getCompetitionId(), command.getRobotId(), command.getNumber());

        final var judgement = firstNonNull(command.getJudgement(), run.getJudgement());
        final var time = firstNonNull(command.getTime(), run.getTime());

        run = run.editResult(judgement, time);
        runRepository.save(run);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ORGANIZER')")
    public RunView getRun(RunQuery query) {
        return getRun(query.getCompetitionId(), query.getRobotId(), query.getNumber());
    }

    private Run getRun(CompetitionId competitionId, RobotId robotId, Integer number) {
        return runRepository.getByCompetitionIdAndRobotIdAndNumber(competitionId, robotId, number)
                .orElseThrow(() -> RunNotFoundException.of(competitionId, robotId, number));
    }
}
