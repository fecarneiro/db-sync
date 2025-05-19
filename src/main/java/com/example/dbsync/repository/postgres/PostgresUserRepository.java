package com.example.dbsync.repository.postgres;

import com.example.dbsync.model.PostgresUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresUserRepository extends JpaRepository<PostgresUserEntity, Long> {
}
