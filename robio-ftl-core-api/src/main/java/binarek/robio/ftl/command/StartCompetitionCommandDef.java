package binarek.robio.ftl.command;

import binarek.robio.shared.model.CompetitionId;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
interface StartCompetitionCommandDef {

    @Value.Parameter
    CompetitionId getCompetitionId();
}
