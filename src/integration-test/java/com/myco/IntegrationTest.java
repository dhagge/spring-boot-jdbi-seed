package com.myco;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.*;

/**
 * Meta annotations for integration tests
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@WebIntegrationTest({"server.port=0", "management.port=0"})
@SpringApplicationConfiguration(classes = ServiceApplication.class)
@TestPropertySource(properties = "spring.config.location=classpath:integration-test.properties")
public @interface IntegrationTest {
}
