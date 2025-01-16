package com.wordle.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This configuration classes is related to Flyway database migration tool.
 * <p>
 * The FlywayConfig class provides two bean definitions for Flyway:
 * <p>
 * 1. flywayInitializer: Initializes Flyway migration with an empty callback.
 * 2. delayedFlywayInitializer: Initializes Flyway migration after the entityManagerFactory bean has been created,
 * only if Flyway is enabled in the application properties.
 *
 * @author Dragan Jovanovic
 * @version 1.0
 * @since 1.0
 **/
@Configuration
public class FlywayConfig {
    /**
     * Creates a {@code FlywayMigrationInitializer} bean that initializes Flyway migration with an empty callback.
     *
     * @param flyway an instance of {@code Flyway} that handles database migration
     * @return a new instance of {@code FlywayMigrationInitializer}
     */
    @Bean
    FlywayMigrationInitializer flywayInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway, f -> {
        });
    }

    /**
     * Creates a {@code delayedFlywayInitializer} bean that initializes Flyway migration only if
     * it is enabled in the application properties.
     *
     * @param flyway           an instance of {@code Flyway} that handles database migration
     * @param flywayProperties an instance of {@code FlywayProperties} that contains Flyway configuration properties
     * @return a new instance of {@code Object}
     */
    @Bean
    @DependsOn("entityManagerFactory")
    Object delayedFlywayInitializer(Flyway flyway, FlywayProperties flywayProperties) {
        if (flywayProperties.isEnabled())
            flyway.migrate();
        return new Object();
    }

    @Bean
    public String getPathString() {
        String relativePath = "wordle/target/classes/words.txt";
        Path path = Paths.get(relativePath).toAbsolutePath().normalize();

        String pathString = path.toString();

        return pathString;
    }
}
