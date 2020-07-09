package binarek.robio.domain.person;

import binarek.robio.domain.common.entity.EntityRepository;

public interface PersonRepository extends EntityRepository<Person, PersonFetchProperties, PersonId, Email> {

    boolean existsByIdAndRole(PersonId id, Person.Role role);
}
