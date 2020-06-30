package binarek.robio.user.domain.person;

import binarek.robio.codegen.BaseStyle;
import binarek.robio.common.domain.entity.Entity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutablePerson.class)
public abstract class Person implements Entity {

    Person() {
    }

    @Override
    public final String getNameValue() {
        return getEmail().getValue();
    }

    @Value.Redacted
    public abstract Email getEmail();

    @Value.Redacted
    public abstract FirstName getFirstName();

    @Value.Redacted
    public abstract LastName getLastName();

    public abstract Role getRole();

    public enum Role {
        COMPETITOR,
        ORGANIZER,
    }
}
