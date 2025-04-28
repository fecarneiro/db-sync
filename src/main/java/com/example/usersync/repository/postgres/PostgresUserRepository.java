package com.example.usersync.repository.postgres;

import com.example.usersync.model.PostgresUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostgresUserRepository extends JpaRepository<PostgresUserEntity, Long> {
}
