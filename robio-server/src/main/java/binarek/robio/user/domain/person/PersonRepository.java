package binarek.robio.user.domain.person;

import binarek.robio.common.domain.entity.EntityRepository;
import binarek.robio.common.persistence.EntityFetchProperties;

public interface PersonRepository extends EntityRepository<Person, EntityFetchProperties.NotSupported, PersonId, Email> {

    boolean existsByIdAndRole(PersonId id, Person.Role role);
}
