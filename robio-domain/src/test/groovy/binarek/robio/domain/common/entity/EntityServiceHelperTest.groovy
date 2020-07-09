package binarek.robio.domain.common.entity

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class EntityServiceHelperTest extends Specification {

    def entityRepository = Mock(EntityRepository)

    def entityValueExtractor = Stub(EntityValueExtractor) {
        getId(entityWithId) >> entityId
        getId(entityWithoutId) >> null
        getName(entityWithId) >> entityName
        getName(entityWithoutId) >> entityName
    }

    @Subject
    def entityServiceHelper = new EntityServiceHelper(Entity, entityRepository, entityValueExtractor)

    @Shared
    def entityWithId = Stub(Entity)

    @Shared
    def entityWithoutId = Stub(Entity)

    @Shared
    def entityId = UUID.fromString('5ec8cacf-b69c-4dbf-afc7-0a3ab87f94b6')

    @Shared
    def entityName = 'Sumo eXtreme'

    def 'throws exception while creating entity with already used id or name'() {
        when:
        entityServiceHelper.createEntity(entityWithId)
        then:
        1 * entityRepository.existsByIdOrName(entityId, entityName) >> true
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
        1 * entityRepository.existsByIdOrName(entityId, entityName) >> false
        1 * entityRepository.insert(entityWithId) >> insertedEntity
        and:
        result == insertedEntity
    }


    def 'throws exception while saving new entity with already used name'() {
        when:
        entityServiceHelper.saveEntity(entityWithoutId)
        then:
        1 * entityRepository.existsByName(entityName) >> true
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
        1 * entityRepository.existsByName(entityName) >> false
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
        entityServiceHelper.deleteEntity(entityId)
        then:
        1 * entityRepository.deleteById(entityId) >> false
        and:
        thrown(EntityNotExistsException)
    }

    def 'deletes existing entity'() {
        when:
        entityServiceHelper.deleteEntity(entityId)
        then:
        1 * entityRepository.deleteById(entityId) >> true
        and:
        noExceptionThrown()
    }


    def "throws exception while getting non-existent entity"() {
        given:
        def fetchProperties = Stub(EntityFetchProperties)
        when:
        entityServiceHelper.getEntity(entityId, fetchProperties)
        then:
        1 * entityRepository.getById(entityId, fetchProperties) >> Optional.empty()
        and:
        thrown(EntityNotExistsException)
    }

    def "gets existing entity by id"() {
        when:
        def result = entityServiceHelper.getEntity(entityId)
        then:
        1 * entityRepository.getById(entityId, null) >> Optional.of(entityWithId)
        and:
        result == entityWithId
    }

    def "gets existing entity by id using fetch properties"() {
        given:
        def fetchProperties = Stub(EntityFetchProperties)
        when:
        def result = entityServiceHelper.getEntity(entityId, fetchProperties)
        then:
        1 * entityRepository.getById(entityId, fetchProperties) >> Optional.of(entityWithId)
        and:
        result == entityWithId
    }
}
