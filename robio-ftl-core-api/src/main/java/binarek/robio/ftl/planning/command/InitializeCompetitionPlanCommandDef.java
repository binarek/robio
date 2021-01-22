package binarek.robio.ftl.planning.command;

import binarek.robio.common.codegen.ValueDefStyle;
import binarek.robio.ftl.planning.model.CompetitionRules;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Value.Immutable
@ValueDefStyle
interface InitializeCompetitionPlanCommandDef {

    UUID getCompetitionId();

    @Nullable
    CompetitionRules getRules();
}
