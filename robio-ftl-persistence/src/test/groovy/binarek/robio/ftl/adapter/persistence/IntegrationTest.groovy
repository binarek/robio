package binarek.robio.ftl.adapter.persistence

import binarek.robio.shared.CallContextProvider
import binarek.robio.shared.RobioSharedPackage
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@ContextConfiguration(classes = RequiredBeansConfiguration)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureEmbeddedDatabase
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface IntegrationTest {

    @TestConfiguration
    @ComponentScan(basePackageClasses = [RobioSharedPackage, RobioFtlPersistencePackage])
    static class RequiredBeansConfiguration {

        @Bean
        CallContextProvider callContextProvider() {
            return () -> CallContextFixture.callContext()
        }
    }
}
