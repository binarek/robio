package binarek.robio.auth.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@ValueDefStyle
abstract class RefreshTokenIdDef extends AbstractSingleValue<UUID> {

    @Value.Parameter
    @Override
    public abstract UUID getValue();
}
