package binarek.robio.registration.persistence.person;

import binarek.robio.common.codegen.BaseMapperConfig;
import binarek.robio.db.tables.records.PersonRecord;
import binarek.robio.common.domain.value.CommonValueMapper;
import binarek.robio.registration.domain.person.Person;
import binarek.robio.registration.domain.person.PersonSortableField;
import binarek.robio.registration.domain.person.PersonValueMapper;
import org.jooq.TableField;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static binarek.robio.db.tables.Person.PERSON;

@Mapper(config = BaseMapperConfig.class, uses = {CommonValueMapper.class, PersonValueMapper.class})
public interface PersonTableMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    void updateRecord(@MappingTarget PersonRecord personRecord, Person person);

    @Mapping(target = "id", source = "externalId")
    Person toPerson(PersonRecord personRecord);

    default TableField<PersonRecord, ?> toField(PersonSortableField personSortableField) {
        switch (personSortableField) {
            case FIRST_NAME:
                return PERSON.FIRST_NAME;
            case LAST_NAME:
                return PERSON.LAST_NAME;
            case ROLE:
                return PERSON.ROLE;
            default:
                throw new IllegalArgumentException("Field of type " + personSortableField + " is not supported");
        }
    }
}
