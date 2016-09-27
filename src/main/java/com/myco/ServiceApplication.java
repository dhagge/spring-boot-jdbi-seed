package com.myco;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * Main services entry point
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = JpaRepositoriesAutoConfiguration.class)
@EnableScheduling
public class ServiceApplication implements HealthIndicator {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @PostConstruct
    public void postConstruct() {
        // set the JVM timezone to UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public Health health() {
        return Health.up().withDetail("health", true).build();
    }

    @Bean
    public Module jodaModule() {
        return new JodaModule();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
