package binarek.robio.ftl.model;

import binarek.robio.ftl.view.CompetitionPlanView;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import binarek.robio.util.codegen.BaseStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.List;

@Value.Immutable
@BaseStyle
public abstract class CompetitionPlan implements CompetitionPlanView {

    CompetitionPlan() {
    }

    public abstract CompetitionId getCompetitionId();

    @Nullable
    public abstract Long getVersion();

    public abstract List<RobotId> getRobots();

    public abstract CompetitionRules getRules();

    public static CompetitionPlan newPlan(CompetitionId competitionId, @Nullable CompetitionRules rules) {
        return ImmutableCompetitionPlan.builder()
                .competitionId(competitionId)
                .rules(rulesOrDefault(rules))
                .build();
    }

    public final CompetitionPlan addRobots(RobotId... robots) {
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
