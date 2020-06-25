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

@Mapper(config = BaseMapperConfig.class, uses = StandardValueMapper.class)
public interface PersonRecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    void updateRecord(@MappingTarget PersonRecord personRecord, Person person);

    @Mapping(target = "id", source = "externalId")
    Person toPerson(PersonRecord personRecord);

    default Email toEmail(String email) {
        return Email.of(email);
    }

    default String toValue(Email email) {
        return email.getValue();
    }

    default FirstName toFirstName(String firstName) {
        return FirstName.of(firstName);
    }

    default String toValue(FirstName firstName) {
        return firstName.getValue();
    }

    default LastName toLastName(String lastName) {
        return LastName.of(lastName);
    }

    default String toValue(LastName lastName) {
        return lastName.getValue();
    }

    default Person.Role toPersonRole(String role) {
        return Person.Role.valueOf(role);
    }

    default String toValue(Person.Role role) {
        return role.name();
    }
}
