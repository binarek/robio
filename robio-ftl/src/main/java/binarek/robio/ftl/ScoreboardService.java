package binarek.robio.ftl;

import binarek.robio.ftl.exception.CompetitionNotFoundException;
import binarek.robio.ftl.model.*;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ScoreboardService {

    private static final Comparator<RobotBestRunTime> BEST_RUN_TIME_COMPARATOR =
            Comparator.comparing(RobotBestRunTime::bestRunTime, Comparator.nullsLast(Comparator.naturalOrder()));

    private static final Comparator<Score> SCORE_COMPARATOR =
            Comparator.comparing(Score::getPosition, Comparator.nullsLast(Comparator.naturalOrder()))
                    .thenComparing(score -> score.getRobotId().getValue());

    private final CompetitionRepository competitionRepository;
    private final RobotRepository robotRepository;
    private final RunRepository runRepository;

    public ScoreboardService(CompetitionRepository competitionRepository,
                             RobotRepository robotRepository,
                             RunRepository runRepository) {
        this.competitionRepository = competitionRepository;
        this.robotRepository = robotRepository;
        this.runRepository = runRepository;
    }

    public Scoreboard getScoreboard(CompetitionId competitionId) {
        if (competitionNotStarted(competitionId)) {
            return Scoreboard.empty();
        }
        return Scoreboard.of(calculateScores(competitionId, new RobotsThatParticipateCompetition(competitionId)));
    }

    private boolean competitionNotStarted(CompetitionId competitionId) {
        return !competitionRepository.getByCompetitionId(competitionId)
                .map(Competition::wasStarted)
                .orElseThrow(() -> CompetitionNotFoundException.of(competitionId));
    }

    private List<Score> calculateScores(CompetitionId competitionId, RobotsThatParticipateCompetition robotsThatParticipateCompetition) {
        final var runs = runRepository.getByCompetitionId(competitionId);

        final var nextScoreFactory = new NextScoreFactory();
        final var participantsScores = runs.stream()
                .filter(run -> robotsThatParticipateCompetition.contains(run.getRobotId()))
                .collect(Collectors.groupingBy(Run::getRobotId))
                .entrySet().stream()
                .map(robotAndRuns -> RobotBestRunTime.of(robotAndRuns.getKey(), robotAndRuns.getValue()))
                .sorted(BEST_RUN_TIME_COMPARATOR)
                .map(nextScoreFactory::build);

        final var nonParticipantsScores = runs.stream()
                .map(Run::getRobotId)
                .distinct()
                .filter(robotId -> !robotsThatParticipateCompetition.contains(robotId))
                .map(Score::newEmptyScore);

        return Stream.concat(participantsScores, nonParticipantsScores)
                .sorted(SCORE_COMPARATOR)
                .toList();
    }

    private record RobotBestRunTime(RobotId robotId, @Nullable RunTime bestRunTime) {

        static RobotBestRunTime of(RobotId robotId, List<Run> runs) {
            return new RobotBestRunTime(robotId, findBestRunTime(runs));
        }

        @Nullable
        private static RunTime findBestRunTime(List<Run> runs) {
            return runs.stream()
                    .filter(Run::isPassed)
                    .map(Run::getTime)
                    .min(Comparator.naturalOrder())
                    .orElse(null);
        }
    }

    private class RobotsThatParticipateCompetition {

        private final CompetitionId competitionId;

        @Nullable
        private Set<RobotId> robotsThatParticipate;

        private RobotsThatParticipateCompetition(CompetitionId competitionId) {
            this.competitionId = competitionId;
        }

        private boolean contains(RobotId robotId) {
            if (robotsThatParticipate == null) {
                robotsThatParticipate = robotRepository.getByCompetitionId(competitionId).stream()
                        .filter(Robot::participatesInCompetition)
                        .map(Robot::getRobotId)
                        .collect(Collectors.toUnmodifiableSet());
            }
            return robotsThatParticipate.contains(robotId);
        }
    }

    private static class NextScoreFactory {

        private int currentPosition = 0;

        @Nullable
        private RunTime currentRunTime = null;

        Score build(RobotBestRunTime robotBestRunTime) {
            if (robotBestRunTime.bestRunTime() == null) {
                return Score.newEmptyScore(robotBestRunTime.robotId());
            } else {
                return Score.newScore(robotBestRunTime.robotId(),
                        resolvePosition(robotBestRunTime.bestRunTime()), robotBestRunTime.bestRunTime());
            }
        }

        private int resolvePosition(RunTime nextRunTime) {
            if (currentRunTime == null || nextRunTime.compareTo(currentRunTime) > 0) {
                currentRunTime = nextRunTime;
                return ++currentPosition;
            } else if (nextRunTime.compareTo(currentRunTime) == 0) {
                return currentPosition;
            } else {
                throw new IllegalStateException("Next run time cannot be less then current run time!");
            }
        }
    }
}
