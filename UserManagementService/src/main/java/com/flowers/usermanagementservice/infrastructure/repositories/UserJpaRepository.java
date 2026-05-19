package com.flowers.usermanagementservice.infrastructure.repositories;

import com.flowers.usermanagementservice.infrastructure.tableentities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {

    List<UserEntity> findByTypeIgnoreCase(String type);

    Optional<UserEntity> findByUsername(String username);
}
