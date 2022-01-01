package binarek.robio.ftl.query;

import binarek.robio.shared.model.CompetitionId;
import binarek.robio.shared.model.RobotId;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
interface RunQueryDef {

    CompetitionId getCompetitionId();

    RobotId getRobotId();

    Integer getNumber();
}
