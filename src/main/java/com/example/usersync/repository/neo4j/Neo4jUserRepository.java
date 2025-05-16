package com.example.usersync.repository.neo4j;

import com.example.usersync.model.Neo4jUserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Service;

public interface Neo4jUserRepository extends Neo4jRepository<Neo4jUserNode, Long> {
}
