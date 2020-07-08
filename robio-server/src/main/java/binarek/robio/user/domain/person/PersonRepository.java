package binarek.robio.user.domain.person;

import binarek.robio.common.domain.entity.EntityRepository;

public interface PersonRepository extends EntityRepository<Person, PersonFetchProperties, PersonId, Email> {

    boolean existsByIdAndRole(PersonId id, Person.Role role);
}
