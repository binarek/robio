package binarek.robio.auth.user.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.immutables.value.Value;

@Value.Immutable
@ValueDefStyle
abstract class UsernameDef extends AbstractSingleValue<String> {

    @Override
    @Value.Parameter
    public abstract String getValue();

    @Value.Check
    protected void validate() {
        final var username = getValue();
        if (!SpecialUsernames.allUsernames().contains(username)) {
            Email.of(username);
        }
    }
}
