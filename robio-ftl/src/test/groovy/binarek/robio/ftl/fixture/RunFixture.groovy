package binarek.robio.ftl.fixture

import binarek.robio.ftl.model.ImmutableRun
import binarek.robio.ftl.model.Run
import binarek.robio.ftl.model.RunJudgement
import binarek.robio.ftl.model.RunTime
import binarek.robio.shared.model.CompetitionId
import binarek.robio.shared.model.RobotId
import org.springframework.lang.Nullable

class RunFixture {

    CompetitionId competitionId
    RobotId robotId
    Integer number
    @Nullable
    Long version
    RunJudgement judgement
    RunTime time

    static RunFixture run(SampleData.RobotData robotData, SampleData.CompetitionData competitionData) {
        return new RunFixture(
                competitionId: competitionData.competitionId,
                robotId: robotData.robotId,
                number: 1,
                version: 1,
                judgement: RunJudgement.PASSED,
                time: RunTime.of(46)
        )
    }

    RunFixture withNumber(Integer number) {
        this.number = number
        return this
    }

    RunFixture withJudgement(RunJudgement judgement) {
        this.judgement = judgement
        return this
    }

    RunFixture withTime(RunTime time) {
        this.time = time
        return this
    }

    Run build() {
        return ImmutableRun.builder()
                .competitionId(competitionId)
                .robotId(robotId)
                .number(number)
                .judgement(judgement)
                .time(time)
                .build()
    }
}
