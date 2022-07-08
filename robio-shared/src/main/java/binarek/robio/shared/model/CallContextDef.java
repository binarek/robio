package binarek.robio.shared.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
interface CallContextDef {

    @Value.Parameter
    User getUser();

    @Value.Parameter
    String getProcessName();

    @Value.Parameter
    CorrelationId getCorrelationId();
}
