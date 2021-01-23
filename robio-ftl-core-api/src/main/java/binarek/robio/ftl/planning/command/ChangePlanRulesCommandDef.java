package binarek.robio.ftl.planning.command;

import binarek.robio.ftl.model.CompetitionRules;
import binarek.robio.shared.model.CompetitionId;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@ValueDefStyle
interface ChangePlanRulesCommandDef {

    @Value.Parameter
    CompetitionId getCompetitionId();

    @Nullable
    @Value.Parameter
    CompetitionRules getRules();
}
