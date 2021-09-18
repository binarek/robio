package binarek.robio.ftl.model;

import binarek.robio.ftl.view.CompetitionPlanView;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.util.codegen.BaseStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@BaseStyle
public abstract class CompetitionPlan implements CompetitionPlanView {

    CompetitionPlan() {
    }

    public abstract CompetitionId getCompetitionId();

    @Nullable
    public abstract Long getVersion();

    public abstract CompetitionRules getRules();

    public static CompetitionPlan initializePlan(CompetitionId competitionId, @Nullable CompetitionRules rules) {
        return ImmutableCompetitionPlan.builder()
                .competitionId(competitionId)
                .rules(rulesOrDefault(rules))
                .build();
    }

    public final CompetitionPlan changeRules(@Nullable CompetitionRules rules) {
        return ImmutableCompetitionPlan.copyOf(this)
                .withRules(rulesOrDefault(rules));
    }

    private static CompetitionRules rulesOrDefault(@Nullable CompetitionRules rules) {
        return rules != null ? rules : CompetitionRules.builder().build();
    }
}
