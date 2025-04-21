package com.example.neo4jdemo.repository;

import com.example.neo4jdemo.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {
    /*
    @Query("MATCH (u:User) RETURN u")
    List<User> findAllUsers();

    @Query("MATCH (u:User)-[r:KNOWS]->(u2:User) RETURN u as user, collect(r), collect(u2)")
    List<User> findAllUsersWithRelationships();


     */
}

