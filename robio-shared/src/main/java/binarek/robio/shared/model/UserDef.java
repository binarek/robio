package binarek.robio.shared.model;

import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
abstract class UserDef {

    @Value.Parameter
    public abstract String getUsername();
}
