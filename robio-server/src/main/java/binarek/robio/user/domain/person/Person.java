package binarek.robio.user.domain.person;

import binarek.robio.codegen.BaseStyle;
import binarek.robio.common.domain.entity.Entity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutablePerson.class)
public interface Person extends Entity {

    @Override
    default String getNameValue() {
        return getEmail().getValue();
    }

    @Value.Redacted
    Email getEmail();

    @Value.Redacted
    FirstName getFirstName();

    @Value.Redacted
    LastName getLastName();

    Role getRole();

    enum Role {
        COMPETITOR,
        ORGANIZER,
    }
}
