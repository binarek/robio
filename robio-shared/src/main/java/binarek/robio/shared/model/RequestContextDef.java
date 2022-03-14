package binarek.robio.shared.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
interface RequestContextDef {

    @Value.Parameter
    User getUser();
}
