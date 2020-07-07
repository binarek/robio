package binarek.robio.user.persistence.person;

import binarek.robio.codegen.BaseMapperConfig;
import binarek.robio.common.domain.value.CommonValueMapper;
import binarek.robio.db.tables.records.PersonRecord;
import binarek.robio.user.domain.person.Person;
import binarek.robio.user.domain.person.PersonValueMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = BaseMapperConfig.class, uses = {CommonValueMapper.class, PersonValueMapper.class})
public interface PersonRecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    void updateRecord(@MappingTarget PersonRecord personRecord, Person person);

    @Mapping(target = "id", source = "externalId")
    Person toPerson(PersonRecord personRecord);
}
