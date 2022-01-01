package binarek.robio.ftl;

import binarek.robio.ftl.model.Robot;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;

import java.util.Collection;
import java.util.Optional;

public interface RobotRepository {

    void save(Robot robot);

    boolean existsByCompetitionIdAndRobotId(CompetitionId competitionId, RobotId robotId);

    Optional<Robot> getByCompetitionIdAndRobotId(CompetitionId competitionId, RobotId robotId);

    Collection<Robot> getByCompetitionId(CompetitionId competitionId);
}
