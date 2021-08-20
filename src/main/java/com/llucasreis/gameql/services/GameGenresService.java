package com.llucasreis.gameql.services;

import com.llucasreis.gameql.domain.entities.GameGenres;
import com.llucasreis.gameql.domain.entities.Genre;
import com.llucasreis.gameql.repositories.GameGenresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GameGenresService {

    private GameGenresRepository gameGenresRepository;

    @Autowired
    public GameGenresService(GameGenresRepository gameGenresRepository) {
        this.gameGenresRepository = gameGenresRepository;
    }

    public Map<Long, List<Genre>> getGenresByGameIds(List<Long> gameIds) {
        List<GameGenres> gameGenres = this.gameGenresRepository.findByGameIdIn(gameIds);
        Map<Long, List<Genre>> response = new HashMap<>();

        gameIds.forEach(gameId -> {
            List<Genre> genres = gameGenres.stream()
                    .filter(gg -> gg.getGame().getId().equals(gameId))
                    .map(GameGenres::getGenre)
                    .collect(Collectors.toList());

            response.put(gameId, genres);
        });

        return response;
    }
}
