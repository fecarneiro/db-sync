package com.example.usersync.config;

import org.neo4j.driver.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Configuration
@EnableNeo4jRepositories(
        basePackages = "com.example.usersync.repository.neo4j",
        transactionManagerRef = "neo4jTransactionManager"
)

public class Neo4jConfig {

    private final Driver driver;

    public Neo4jConfig(Driver driver) {
        this.driver = driver;
    }

    @Bean("neo4jTransactionManager")
    public Neo4jTransactionManager neo4jTransactionManager() {
        return new Neo4jTransactionManager(driver);
    }
}

