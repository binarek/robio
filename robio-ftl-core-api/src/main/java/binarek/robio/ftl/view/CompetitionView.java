package binarek.robio.ftl.view;

import binarek.robio.ftl.model.CompetitionRules;
import binarek.robio.ftl.model.CompetitionState;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import org.springframework.lang.Nullable;

import java.time.ZonedDateTime;
import java.util.Set;

public interface CompetitionView {

    CompetitionId getCompetitionId();

    Set<RobotId> getRobots();

    CompetitionRules getRules();

    CompetitionState getState();

    ZonedDateTime getStartDateTime();

    @Nullable
    ZonedDateTime getFinishDateTime();
}
