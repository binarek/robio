package binarek.robio.domain.person;

import binarek.robio.util.codegen.BaseStyle;
import binarek.robio.domain.common.entity.Entity;
import binarek.robio.domain.common.value.Notes;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;
import org.springframework.lang.Nullable;

@Value.Immutable
@BaseStyle
@JsonDeserialize(as = ImmutablePerson.class)
public abstract class Person implements Entity {

    Person() {
    }

    @Nullable
    public abstract PersonId getId();

    @Nullable
    public abstract Long getVersion();

    @Value.Redacted
    public abstract Email getEmail();

    @Value.Redacted
    public abstract FirstName getFirstName();

    @Value.Redacted
    public abstract LastName getLastName();

    public abstract Role getRole();

    @Nullable
    public abstract Notes getNotes();

    public enum Role {
        COMPETITOR,
        ORGANIZER,
    }
}
