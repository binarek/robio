package binarek.robio.user.domain.person;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static binarek.robio.common.util.MapperUtil.mapNullSafe;

@Component
public class PersonValueMapper {

    @Nullable
    public PersonId toPeronId(@Nullable UUID id) {
        return mapNullSafe(id, PersonId::of);
    }

    @Nullable
    public UUID toValue(@Nullable PersonId id) {
        return mapNullSafe(id, PersonId::getValue);
    }

    @Nullable
    public Email toEmail(@Nullable String email) {
        return mapNullSafe(email, Email::of);
    }

    @Nullable
    public String toValue(@Nullable Email email) {
        return mapNullSafe(email, Email::getValue);
    }

    @Nullable
    public FirstName toFirstName(@Nullable String firstName) {
        return mapNullSafe(firstName, FirstName::of);
    }

    @Nullable
    public String toValue(@Nullable FirstName firstName) {
        return mapNullSafe(firstName, FirstName::getValue);
    }

    @Nullable
    public LastName toLastName(@Nullable String lastName) {
        return mapNullSafe(lastName, LastName::of);
    }

    @Nullable
    public String toValue(@Nullable LastName lastName) {
        return mapNullSafe(lastName, LastName::getValue);
    }

    @Nullable
    public Person.Role toPersonRole(@Nullable String personRole) {
        return mapNullSafe(personRole, Person.Role::valueOf);
    }

    @Nullable
    public String toValue(@Nullable Person.Role role) {
        return mapNullSafe(role, Person.Role::name);
    }
}
