package binarek.robio.common.domain.entity

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class EntityServiceHelperTest extends Specification {

    def entityRepository = Mock(EntityRepository)

    @Subject
    def entityServiceHelper = new EntityServiceHelper(Entity, entityRepository)

    @Shared
    def entityWithId = Stub(Entity) {
        getIdValue() >> UUID.fromString('5ec8cacf-b69c-4dbf-afc7-0a3ab87f94b6')
        getNameValue() >> 'Sumo eXtreme'
    }

    @Shared
    def entityWithoutId = Stub(Entity) {
        getIdValue() >> null
        getNameValue() >> 'Follow The Line'
    }

    def 'throws exception while creating entity with already used id or name'() {
        when:
        entityServiceHelper.createEntity(entityWithId)
        then:
        1 * entityRepository.existsByIdOrName(entityWithId.idValue, entityWithId.nameValue) >> true
        0 * entityRepository./insert.*/
        and:
        thrown(EntityAlreadyExistsException)
    }

    def 'creates new entity'() {
        given:
        def insertedEntity = Stub(Entity)
        when:
        def result = entityServiceHelper.createEntity(entityWithId)
        then:
        1 * entityRepository.existsByIdOrName(entityWithId.idValue, entityWithId.nameValue) >> false
        1 * entityRepository.insert(entityWithId) >> insertedEntity
        and:
        result == insertedEntity
    }


    def 'throws exception while saving new entity with already used name'() {
        when:
        entityServiceHelper.saveEntity(entityWithoutId)
        then:
        1 * entityRepository.existsByName(entityWithoutId.nameValue) >> true
        0 * entityRepository./insert.*/
        and:
        thrown(EntityAlreadyExistsException)
    }

    def 'saves new entity without used name'() {
        given:
        def insertedEntity = Stub(Entity)
        when:
        def result = entityServiceHelper.saveEntity(entityWithoutId)
        then:
        1 * entityRepository.existsByName(entityWithoutId.nameValue) >> false
        1 * entityRepository.insertOrUpdate(entityWithoutId) >> insertedEntity
        and:
        result == insertedEntity
    }

    def 'updates existing entity'() {
        given:
        def insertedEntity = Stub(Entity)
        when:
        def result = entityServiceHelper.saveEntity(entityWithId)
        then:
        1 * entityRepository.insertOrUpdate(entityWithId) >> insertedEntity
        and:
        result == insertedEntity
    }


    def 'throws exception while deleting non-existent entity'() {
        when:
        entityServiceHelper.deleteEntity(entityWithId.idValue)
        then:
        entityRepository.deleteById(entityWithId.idValue) >> false
        and:
        thrown(EntityNotExistsException)
    }

    def 'deletes existing entity'() {
        when:
        entityServiceHelper.deleteEntity(entityWithId.idValue)
        then:
        entityRepository.deleteById(entityWithId.idValue) >> true
        and:
        noExceptionThrown()
    }


    def "throws exception while getting non-existent entity"() {
        given:
        def fetchProperties = Stub(EntityFetchProperties)
        when:
        entityServiceHelper.getEntity(entityWithId.idValue, fetchProperties)
        then:
        entityRepository.getById(entityWithId.idValue, fetchProperties) >> Optional.empty()
        and:
        thrown(EntityNotExistsException)
    }

    def "gets existing entity by id"() {
        when:
        def result = entityServiceHelper.getEntity(entityWithId.idValue)
        then:
        entityRepository.getById(entityWithId.idValue, null) >> Optional.of(entityWithId)
        and:
        result == entityWithId
    }

    def "gets existing entity by id using fetch properties"() {
        given:
        def fetchProperties = Stub(EntityFetchProperties)
        when:
        def result = entityServiceHelper.getEntity(entityWithId.idValue, fetchProperties)
        then:
        entityRepository.getById(entityWithId.idValue, fetchProperties) >> Optional.of(entityWithId)
        and:
        result == entityWithId
    }
}
