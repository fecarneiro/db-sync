package com.example.dbsync.repository.neo4j;

import com.example.dbsync.model.Neo4jUserNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Neo4jUserRepository extends Neo4jRepository<Neo4jUserNode, Long> {
    Optional<Neo4jUserNode> findByUsername(String username);
}
