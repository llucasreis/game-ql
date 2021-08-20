package com.llucasreis.gameql.repositories;

import com.llucasreis.gameql.domain.entities.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {

    Optional<Platform> findByNameIgnoreCase(String name);
}
