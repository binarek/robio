package binarek.robio.ftl;

import binarek.robio.ftl.model.Run;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;

import java.util.Optional;

public interface RunRepository {

    void save(Run run);

    int countByCompetitionIdAndRobotId(CompetitionId competitionId, RobotId robotId);

    Optional<Run> getByCompetitionIdAndRobotIdAndNumber(CompetitionId competitionId, RobotId robotId, Integer number);

    Optional<Run> getByCompetitionIdAndRobotIdAndMaxNumber(CompetitionId competitionId, RobotId robotId);
}
