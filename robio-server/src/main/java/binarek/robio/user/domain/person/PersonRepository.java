package binarek.robio.user.domain.person;

import binarek.robio.common.domain.entity.EntityRepository;

import java.util.UUID;

public interface PersonRepository extends EntityRepository<Person, PersonFetchProperties> {

    boolean existsByIdAndRole(UUID id, Person.Role role);
}
