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

    public abstract ZonedDateTime getInitializeDateTime();

    @Nullable
    public abstract ZonedDateTime getStartDateTime();

    @Nullable
    public abstract ZonedDateTime getFinishDateTime();

    public final boolean wasStarted() {
        return getState() != CompetitionState.INITIALIZED;
    }

    public final Competition start(ZonedDateTime startDateTime) {
        return ImmutableCompetition.builder()
                .from(this)
                .state(CompetitionState.STARTED)
                .startDateTime(startDateTime)
                .build();
    }

    public static Competition initialize(CompetitionId competitionId, @Nullable CompetitionRules rules, ZonedDateTime initializeDateTime) {
        return ImmutableCompetition.builder()
                .competitionId(competitionId)
                .rules(rulesOrDefault(rules))
                .state(CompetitionState.INITIALIZED)
                .initializeDateTime(initializeDateTime)
                .build();
    }

    public final Competition changeRules(@Nullable CompetitionRules rules) {
        return ImmutableCompetition.copyOf(this)
                .withRules(rulesOrDefault(rules));
    }

    private static CompetitionRules rulesOrDefault(@Nullable CompetitionRules rules) {
        return rules != null ? rules : CompetitionRules.builder().build();
    }
}
