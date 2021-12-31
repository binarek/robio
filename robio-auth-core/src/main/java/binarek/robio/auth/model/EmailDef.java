package binarek.robio.auth.model;

import binarek.robio.util.codegen.AbstractSingleValue;
import binarek.robio.util.codegen.ValueDefStyle;
import org.apache.commons.lang3.Validate;
import org.apache.commons.validator.routines.EmailValidator;
import org.immutables.value.Value;

import static binarek.robio.util.StringUtil.isTrimmed;

@Value.Immutable
@ValueDefStyle
abstract class EmailDef extends AbstractSingleValue<String> {

    @Override
    @Value.Parameter
    public abstract String getValue();

    @Value.Check
    protected EmailDef normalizeAndValidate() {
        final var email = getValue();
        if (!isTrimmed(email)) {
            return Email.of(email.trim());
        } else {
            Validate.isTrue(EmailValidator.getInstance().isValid(email), "Invalid email format");
            return this;
        }
    }
}
