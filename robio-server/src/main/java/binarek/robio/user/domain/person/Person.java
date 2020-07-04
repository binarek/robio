package binarek.robio.user.domain.person;

import binarek.robio.codegen.BaseStyle;
import binarek.robio.common.domain.entity.Entity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutablePerson.class)
public abstract class Person implements Entity {

    Person() {
    }

    @Override
    public final UUID getIdValue() {
        return getId() != null ? getId().getValue() : null;
    }

    @Override
    public final String getNameValue() {
        return getEmail().getValue();
    }

    @Nullable
    public abstract PersonId getId();

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
