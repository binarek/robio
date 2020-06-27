package binarek.robio.user.persistence.person;

import binarek.robio.codegen.BaseMapperConfig;
import binarek.robio.common.domain.value.StandardValueMapper;
import binarek.robio.db.tables.records.PersonRecord;
import binarek.robio.user.domain.person.Email;
import binarek.robio.user.domain.person.FirstName;
import binarek.robio.user.domain.person.LastName;
import binarek.robio.user.domain.person.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.lang.Nullable;

import static binarek.robio.common.util.MapperUtil.mapNullSafe;

@Mapper(config = BaseMapperConfig.class, uses = StandardValueMapper.class)
public interface PersonRecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    void updateRecord(@MappingTarget PersonRecord personRecord, Person person);

    @Mapping(target = "id", source = "externalId")
    Person toPerson(PersonRecord personRecord);

    @Nullable
    default Email toEmail(@Nullable String email) {
        return mapNullSafe(email, Email::of);
    }

    @Nullable
    default String toValue(@Nullable Email email) {
        return mapNullSafe(email, Email::getValue);
    }

    @Nullable
    default FirstName toFirstName(@Nullable String firstName) {
        return mapNullSafe(firstName, FirstName::of);
    }

    @Nullable
    default String toValue(@Nullable FirstName firstName) {
        return mapNullSafe(firstName, FirstName::getValue);
    }

    @Nullable
    default LastName toLastName(@Nullable String lastName) {
        return mapNullSafe(lastName, LastName::of);
    }

    @Nullable
    default String toValue(@Nullable LastName lastName) {
        return mapNullSafe(lastName, LastName::getValue);
    }

    @Nullable
    default Person.Role toPersonRole(@Nullable String role) {
        return mapNullSafe(role, Person.Role::valueOf);
    }

    @Nullable
    default String toValue(@Nullable Person.Role role) {
        return mapNullSafe(role, Person.Role::name);
    }
}
