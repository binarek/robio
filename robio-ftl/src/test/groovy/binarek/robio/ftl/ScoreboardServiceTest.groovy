package binarek.robio.ftl

import binarek.robio.ftl.model.*
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static binarek.robio.ftl.fixture.CompetitionFixture.competition
import static binarek.robio.ftl.fixture.RobotFixture.robot
import static binarek.robio.ftl.fixture.RunFixture.run
import static binarek.robio.ftl.fixture.SampleData.CompetitionData.ftl1
import static binarek.robio.ftl.fixture.SampleData.RobotData.*

class ScoreboardServiceTest extends Specification {

    def competitionRepository = Stub(CompetitionRepository)
    def robotRepository = Stub(RobotRepository)
    def runRepository = Stub(RunRepository)

    @Subject
    ScoreboardService scoreboardService = new ScoreboardService(competitionRepository, robotRepository, runRepository)

    @Unroll
    def 'calculates current scoreboard for case: #testCase.name'() {
        given:
        def competition = competition(ftl1).build()
                .start(ftl1.initializeDate.plusDays(30))
        def competitionId = competition.getCompetitionId()

        competitionRepository.getByCompetitionId(competitionId) >> Optional.of(competition)
        robotRepository.getByCompetitionId(competitionId) >> testCase.givenRobotsThatParticipateCompetition
        runRepository.getByCompetitionId(competitionId) >> testCase.givenRuns

        expect:
        scoreboardService.getScoreboard(competitionId) == testCase.expectedScoreboard

        where:
        testCase << [noRuns(),
                     multipleRunsBySingleRobot(),
                     withFailedResults(),
                     multipleRobots(),
                     robotWithAllRunsFailed(),
                     disqualifiedRobot(),
                     complexCase()]
    }

    static class TestCase {
        String name
        Run[] givenRuns
        Robot[] givenRobotsThatParticipateCompetition
        Scoreboard expectedScoreboard
    }

    TestCase noRuns() {
        return new TestCase(
                name: 'no runs',
                givenRuns: [],
                givenRobotsThatParticipateCompetition: [dixyRobot(), moricRobot(), finkyRobot()],
                expectedScoreboard: Scoreboard.empty()
        )
    }

    TestCase multipleRunsBySingleRobot() {
        return new TestCase(
                name: 'multiple runs by single robot',
                givenRuns: [
                        dixyRun(1, RunJudgement.PASSED, RunTime.of(10)),
                        dixyRun(2, RunJudgement.PASSED, RunTime.of(123)),
                        dixyRun(3, RunJudgement.PASSED, RunTime.of(4)),
                        dixyRun(4, RunJudgement.PASSED, RunTime.of(21)),
                ],
                givenRobotsThatParticipateCompetition: [
                        dixyRobot().qualify()
                ],
                expectedScoreboard: Scoreboard.of([
                        dixyScore(1, RunTime.of(4)),
                ])
        )
    }

    TestCase withFailedResults() {
        return new TestCase(
                name: 'with failed results',
                givenRuns: [
                        dixyRun(1, RunJudgement.FAILED, RunTime.of(10)),
                        dixyRun(2, RunJudgement.PASSED, RunTime.of(123)),
                ],
                givenRobotsThatParticipateCompetition: [
                        dixyRobot().qualify()
                ],
                expectedScoreboard: Scoreboard.of([
                        dixyScore(1, RunTime.of(123)),
                ])
        )
    }

    TestCase multipleRobots() {
        return new TestCase(
                name: 'multiple robots',
                givenRuns: [
                        dixyRun(1, RunJudgement.PASSED, RunTime.of(10)),
                        dixyRun(2, RunJudgement.FAILED, RunTime.of(123)),
                        moricRun(1, RunJudgement.PASSED, RunTime.of(15)),
                        moricRun(2, RunJudgement.PASSED, RunTime.of(4)),
                        finkyRun(1, RunJudgement.PASSED, RunTime.of(12)),
                ],
                givenRobotsThatParticipateCompetition: [
                        dixyRobot().qualify(),
                        moricRobot().qualify(),
                        finkyRobot().qualify(),
                ],
                expectedScoreboard: Scoreboard.of([
                        moricScore(1, RunTime.of(4)),
                        dixyScore(2, RunTime.of(10)),
                        finkyScore(3, RunTime.of(12)),
                ])
        )
    }

    TestCase robotWithAllRunsFailed() {
        return new TestCase(
                name: 'robot with all runs failed',
                givenRuns: [
                        dixyRun(1, RunJudgement.FAILED, RunTime.of(10)),
                        dixyRun(2, RunJudgement.FAILED, RunTime.of(123)),
                        moricRun(1, RunJudgement.PASSED, RunTime.of(15)),
                        moricRun(2, RunJudgement.FAILED, RunTime.of(4)),
                        finkyRun(1, RunJudgement.PASSED, RunTime.of(12)),
                ],
                givenRobotsThatParticipateCompetition: [
                        dixyRobot().qualify(),
                        moricRobot().qualify(),
                        finkyRobot().qualify(),
                ],
                expectedScoreboard: Scoreboard.of([
                        finkyScore(1, RunTime.of(12)),
                        moricScore(2, RunTime.of(15)),
                        dixyEmptyScore(),
                ])
        )
    }

    TestCase disqualifiedRobot() {
        return new TestCase(
                name: 'with disqualified robot',
                givenRuns: [
                        dixyRun(1, RunJudgement.PASSED, RunTime.of(10)),
                        dixyRun(2, RunJudgement.PASSED, RunTime.of(123)),
                        moricRun(1, RunJudgement.PASSED, RunTime.of(15)),
                        moricRun(1, RunJudgement.PASSED, RunTime.of(4)),
                        finkyRun(1, RunJudgement.PASSED, RunTime.of(12)),
                ],
                givenRobotsThatParticipateCompetition: [
                        dixyRobot().disqualify(),
                        moricRobot().qualify(),
                        finkyRobot().qualify(),
                ],
                expectedScoreboard: Scoreboard.of([
                        moricScore(1, RunTime.of(4)),
                        finkyScore(2, RunTime.of(12)),
                        dixyEmptyScore(),
                ])
        )
    }

    TestCase complexCase() {
        return new TestCase(
                name: 'complex case',
                givenRuns: [
                        dixyRun(1, RunJudgement.FAILED, RunTime.of(10)),
                        dixyRun(2, RunJudgement.PASSED, RunTime.of(123)),
                        moricRun(1, RunJudgement.PASSED, RunTime.of(15)),
                        moricRun(2, RunJudgement.FAILED, RunTime.of(4)),
                        finkyRun(1, RunJudgement.FAILED, RunTime.of(12)),
                ],
                givenRobotsThatParticipateCompetition: [
                        dixyRobot().qualify(),
                        moricRobot().disqualify(),
                        finkyRobot().qualify(),
                ],
                expectedScoreboard: Scoreboard.of([
                        dixyScore(1, RunTime.of(123)),
                        moricEmptyScore(),
                        finkyEmptyScore(),
                ])
        )
    }

    Robot dixyRobot() {
        return robot(dixy, ftl1).build()
    }

    Robot moricRobot() {
        return robot(moric, ftl1).build()
    }

    Robot finkyRobot() {
        return robot(finky, ftl1).build()
    }

    Run dixyRun(int number, RunJudgement judgement, RunTime time) {
        return run(dixy, ftl1)
                .withNumber(number)
                .withJudgement(judgement)
                .withTime(time)
                .build()
    }

    Run moricRun(int number, RunJudgement judgement, RunTime time) {
        return run(moric, ftl1)
                .withNumber(number)
                .withJudgement(judgement)
                .withTime(time)
                .build()
    }

    Run finkyRun(int number, RunJudgement judgement, RunTime time) {
        return run(finky, ftl1)
                .withNumber(number)
                .withJudgement(judgement)
                .withTime(time)
                .build()
    }

    Score dixyScore(Integer position, RunTime bestRunTime) {
        return Score.newScore(dixy.robotId, position, bestRunTime)
    }

    Score dixyEmptyScore() {
        return Score.newEmptyScore(dixy.robotId)
    }

    Score moricScore(Integer position, RunTime bestRunTime) {
        return Score.newScore(moric.robotId, position, bestRunTime)
    }

    Score moricEmptyScore() {
        return Score.newEmptyScore(moric.robotId)
    }

    Score finkyScore(Integer position, RunTime bestRunTime) {
        return Score.newScore(finky.robotId, position, bestRunTime)
    }

    Score finkyEmptyScore() {
        return Score.newEmptyScore(finky.robotId)
    }
}
