package com.llucasreis.gameql.dataloaders;

import com.llucasreis.gameql.domain.entities.GameGenres;
import com.llucasreis.gameql.domain.entities.Genre;
import com.llucasreis.gameql.repositories.GameGenresRepository;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.BatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@DgsDataLoader(name = "genres")
public class GenreDataLoader implements BatchLoader<Long, Genre> {

    private GameGenresRepository gameGenresRepository;

    @Autowired
    public GenreDataLoader(GameGenresRepository gameGenresRepository) {
        this.gameGenresRepository = gameGenresRepository;
    }

    @Override
    public CompletionStage<List<Genre>> load(List<Long> gameIds) {
        return CompletableFuture.supplyAsync(() -> this.gameGenresRepository
                .findByGameIdIn(gameIds)
                .stream()
                .map(GameGenres::getGenre)
                .collect(Collectors.toList()));
    }
}
