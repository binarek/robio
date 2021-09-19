package binarek.robio.ftl.view;

import binarek.robio.ftl.model.CompetitionRules;
import binarek.robio.ftl.model.CompetitionState;
import binarek.robio.shared.model.CompetitionId;
import org.springframework.lang.Nullable;

import java.time.ZonedDateTime;

public interface CompetitionView {

    CompetitionId getCompetitionId();

    CompetitionRules getRules();

    CompetitionState getState();

    ZonedDateTime getInitializeDateTime();

    @Nullable
    ZonedDateTime getStartDateTime();

    @Nullable
    ZonedDateTime getFinishDateTime();
}
