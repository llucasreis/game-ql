package com.llucasreis.gameql.repositories;

import com.llucasreis.gameql.domain.entities.GameGenres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameGenresRepository extends JpaRepository<GameGenres, Long> {
    List<GameGenres> findByGameIdIn(List<Long> gameIds);
}
