package binarek.robio.user.domain.person;

import binarek.robio.common.domain.entity.EntityRepository;
import binarek.robio.common.persistence.EntityFetchProperties;

import java.util.UUID;

public interface PersonRepository extends EntityRepository<Person, EntityFetchProperties.NotSupported> {

    boolean existsByIdAndRole(UUID id, Person.Role role);
}
