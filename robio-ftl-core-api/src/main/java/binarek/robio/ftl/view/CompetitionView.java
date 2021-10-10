package binarek.robio.ftl.view;

import binarek.robio.ftl.model.CompetitionRules;
import binarek.robio.ftl.model.CompetitionState;
import binarek.robio.shared.model.BusinessDateTime;
import binarek.robio.shared.model.CompetitionId;
import org.springframework.lang.Nullable;

public interface CompetitionView {

    CompetitionId getCompetitionId();

    CompetitionRules getRules();

    CompetitionState getState();

    BusinessDateTime getInitializeDateTime();

    @Nullable
    BusinessDateTime getStartDateTime();

    @Nullable
    BusinessDateTime getFinishDateTime();
}
