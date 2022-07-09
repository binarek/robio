package binarek.robio.auth.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
abstract class HashedPasswordDef extends AbstractSingleValue<String> {

    @Override
    @Value.Parameter
    @Value.Redacted
    public abstract String getValue();
}
