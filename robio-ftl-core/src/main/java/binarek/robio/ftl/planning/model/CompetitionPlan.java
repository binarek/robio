package binarek.robio.ftl.planning.model;

import binarek.robio.ftl.model.CompetitionRules;
import binarek.robio.ftl.planning.view.CompetitionPlanView;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.util.codegen.BaseStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.List;

@Value.Immutable
@BaseStyle
@SuppressWarnings("immutables:from")    // NOTE: do not use .from(CompetitionPlanView) - this should never happen
public abstract class CompetitionPlan implements CompetitionPlanView {

    CompetitionPlan() {
    }

    public abstract CompetitionId getCompetitionId();

    @Nullable
    public abstract Long getVersion();

    public abstract List<RobotPlaceholder> getRobots();

    public abstract CompetitionRules getRules();

    public final boolean canBeApplied() {
        return getRobots().stream().allMatch(RobotPlaceholder::isReady);
    }

    public static CompetitionPlan newPlan(CompetitionId competitionId, @Nullable CompetitionRules rules) {
        return ImmutableCompetitionPlan.builder()
                .competitionId(competitionId)
                .rules(rulesOrDefault(rules))
                .build();
    }

    public final CompetitionPlan addRobots(RobotPlaceholder... robots) {
        return ImmutableCompetitionPlan.builder()
                .from(this)
                .addRobots(robots)
                .build();
    }

    public final CompetitionPlan changeRules(@Nullable CompetitionRules rules) {
        return ImmutableCompetitionPlan.builder()
                .from(this)
                .rules(rulesOrDefault(rules))
                .build();
    }

    private static CompetitionRules rulesOrDefault(@Nullable CompetitionRules rules) {
        return rules != null ? rules : CompetitionRules.builder().build();
    }
}
