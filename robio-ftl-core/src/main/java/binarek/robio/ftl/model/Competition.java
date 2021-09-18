package binarek.robio.ftl.model;

import binarek.robio.ftl.view.CompetitionView;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.util.codegen.BaseStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.time.ZonedDateTime;

@Value.Immutable
@BaseStyle
public abstract class Competition implements CompetitionView {

    Competition() {
    }

    public abstract CompetitionId getCompetitionId();

    @Nullable
    public abstract Long getVersion();

    public abstract CompetitionRules getRules();

    public abstract CompetitionState getState();

    public abstract ZonedDateTime getStartDateTime();

    @Nullable
    public abstract ZonedDateTime getFinishDateTime();

    public static Competition start(CompetitionPlan plan, ZonedDateTime startDateTime) {
        return ImmutableCompetition.builder()
                .competitionId(plan.getCompetitionId())
                .rules(plan.getRules())
                .state(CompetitionState.STARTED)
                .startDateTime(startDateTime)
                .build();
    }
}
