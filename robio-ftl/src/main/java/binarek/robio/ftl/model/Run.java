package binarek.robio.ftl.model;

import binarek.robio.ftl.view.RunView;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import binarek.robio.util.codegen.BaseStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@BaseStyle
public abstract class Run implements RunView {

    private static final int FIRST_RUN_NUMBER = 1;
    private static final int RUN_NUMBER_INCREMENT = 1;

    Run() {
    }

    public abstract CompetitionId getCompetitionId();

    public abstract RobotId getRobotId();

    public abstract Integer getNumber();

    @Nullable
    public abstract Long getVersion();

    public abstract RunJudgement getJudgement();

    public abstract RunTime getTime();

    public final boolean isPassed() {
        return getJudgement() == RunJudgement.PASSED;
    }

    public static Run firstRun(CompetitionId competitionId, RobotId robotId, RunJudgement judgement, RunTime time) {
        return ImmutableRun.builder()
                .competitionId(competitionId)
                .robotId(robotId)
                .number(FIRST_RUN_NUMBER)
                .judgement(judgement)
                .time(time)
                .build();
    }

    public static Run nextRun(Run previousRun, RunJudgement judgement, RunTime time) {
        return ImmutableRun.builder()
                .competitionId(previousRun.getCompetitionId())
                .robotId(previousRun.getRobotId())
                .number(previousRun.getNumber() + RUN_NUMBER_INCREMENT)
                .judgement(judgement)
                .time(time)
                .build();
    }

    public Run editResult(RunJudgement judgement, RunTime time) {
        return ImmutableRun.builder()
                .from(this)
                .judgement(judgement)
                .time(time)
                .build();
    }
}
