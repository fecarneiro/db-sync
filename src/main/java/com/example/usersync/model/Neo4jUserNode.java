package com.example.usersync.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("User")
@Getter
@Setter
@NoArgsConstructor
public class Neo4jUserNode {

    @Id
    private Long id;

    private String username;

    public Neo4jUserNode(Long id, String username) {
        this.id = id;
        this.username = username;
    }


}
