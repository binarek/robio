package binarek.robio.auth.user.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
@ValueDefStyle
abstract class UserIdDef extends AbstractSingleValue<UUID> {

    @Value.Parameter
    @Override
    public abstract UUID getValue();
}
