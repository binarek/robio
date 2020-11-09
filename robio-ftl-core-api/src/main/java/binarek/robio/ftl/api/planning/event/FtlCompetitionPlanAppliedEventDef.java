package binarek.robio.ftl.api.planning.event;

import binarek.robio.common.codegen.ValueDefStyle;
import binarek.robio.common.core.shared.BusinessEvent;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@ValueDefStyle
interface FtlCompetitionPlanAppliedEventDef extends BusinessEvent {

    UUID competitionId();
}
